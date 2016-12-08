package com.hdmb.ireader.txtreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

import com.hdmb.ireader.txtreader.bean.Page;
import com.hdmb.ireader.txtreader.bean.TxtBitmapCache;
import com.hdmb.ireader.txtreader.bean.TxtError;
import com.hdmb.ireader.txtreader.bean.TxtFile;
import com.hdmb.ireader.txtreader.pipeline.ModeTransformer;
import com.hdmb.ireader.txtreader.pipeline.TxtPipeline;
import com.hdmb.ireader.txtreader.utils.BitmapUtils;
import com.hdmb.ireader.txtreader.utils.FileCharsetDetector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:29
 * 文件    TbReader
 * 描述
 */
public class TxtModel {
    private File txtfile;
    private Context context;
    private TxtManager txtManager;
    private TxtPipeline txtPipeline;
    private TxtBitmapCache bitmapCache;
    private TxtPageDB db;
    private Page prevPage;
    private Page middPage;
    private Page nextPage;
    private int pageNums = -1;
    private ModeTransformer modeTransformer;

    private boolean pauseSaparateThread = false;
    private boolean stopSaparateThread = false;
    private boolean saparateDone = false;
    private Map<Integer, Page> bufferCaches;

    public TxtModel(Context context, TxtManager txtManager, TxtFile file) {
        this.context = context;
        this.txtManager = txtManager;
        TxtError txtError = new TxtError();
        txtError.setTxtErrorCode(TxtErrorCode.LOAD_BOOK_EXCEPTION);
        txtManager.setTransformer(new ManagerTransformerImpl());
        try {
            txtfile = new File(file.getFilePath());
        } catch (Exception e) {
            txtError.setTxtErrorMsg("文件路径为空");
            txtManager.onTxtLoadError(txtError);
            return;
        }
        if (!txtfile.exists()) {
            txtError.setTxtErrorMsg("文件不存在");
            txtManager.onTxtLoadError(txtError);
            return;
        }

        txtPipeline = new TxtPipeline();
        bitmapCache = new TxtBitmapCache();
        bufferCaches = new HashMap<>();
        refreshPageBitmap();
    }

    private void refreshPageBitmap() {
        bitmapCache.setPageBitmap(BitmapUtils.createBitmapTiledBackground(context.getResources(),
                txtManager.getConfig().getBackgroundColor(), txtManager.getWidth(), txtManager.getHeight()));
    }

    public void setModelTransformer(Transformer transformer) {
        this.modeTransformer = (ModeTransformer) transformer;
    }

    public TxtBitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public void loadTxtBook(Transformer transformer) throws IOException {
        String charsetName = getCharsetName();
        txtPipeline.loadTxtFile(txtfile, charsetName, transformer);
    }

    /**
     * 去数据库中查找已经分页好的数据
     *
     * @param pageIndex 页码
     * @return 如果没找到返回空
     */
    public Page getPage(int pageIndex) {
        if (db != null) {
            Page page = db.getPageByInedx(pageIndex);
            if (page != null) {
                page = getPageFromPosition(page.getFirstElementParagraphIndex(), page.getFirstElementCharIndex(),
                        txtManager.getTextPaint(), txtManager.getWidth(), txtManager.getLineSize());
                return page;
            }
        }
        return null;
    }

    /**
     * 必须分页完成后执行这个才有效果
     *
     * @param pageIndex 页码
     */
    public void loadPage(int pageIndex) {
        if (pageIndex > 0 && pageIndex < pageNums) {
            if (pageIndex == 1) {
                loadFirstPage();
            } else {
                Page page = db.getPageByInedx(pageIndex - 1);
                if (page != null) {
                    loadFromChar(page.getFirstElementParagraphIndex(), page.getFirstElementCharIndex(), pageIndex - 1);
                    if (pageIndex >= pageNums) {
                        modeTransformer.onLoadPageFromIndex(false, true);
                    } else {
                        modeTransformer.onLoadPageFromIndex(false, false);
                    }
                }
            }
        }
    }

    public void loadNextPage() {
        prevPage = middPage;
        middPage = nextPage;
        boolean isLastPage = false;
        int nextPageIndex = middPage.getPageIndex() + 1;
        if (middPage != null && !isEmpty(middPage)) {
            if (bufferCaches.containsKey(nextPageIndex)) {
                nextPage = bufferCaches.get(nextPageIndex);
            } else {
                nextPage = getPageFromPosition(middPage.getLastElementParagraphIndex(),
                        middPage.getLastElementCharIndex(), txtManager.getTextPaint(),
                        txtManager.getWidth(), txtManager.getLineSize());
            }
            if (nextPage != null && !isEmpty(nextPage)) {
                nextPage.setPageIndex(nextPageIndex);
            } else {
                isLastPage = true;
            }
        } else {
            isLastPage = true;
        }
        saveNextBitmapCache();
        modeTransformer.onLoadNextPage(isLastPage);
    }

    public void loadPrevPage() {
        nextPage = middPage;
        middPage = prevPage;
        int prevPageIndex = middPage.getPageIndex() - 1;
        if (bufferCaches.containsKey(prevPageIndex)) {
            prevPage = bufferCaches.get(prevPageIndex);
        } else {
            prevPage = getPage(prevPageIndex);
        }
        boolean isFirstPage = false;
        if (prevPage == null) {
            prevPage = new Page();
            prevPage.setFirstPage(true);
            isFirstPage = true;
        }
        prevPage.setPageIndex(prevPageIndex);
        savePrevBitmapCache();
        modeTransformer.onLoadPrevPage(isFirstPage);
    }

    private void savePrevBitmapCache() {
        bitmapCache.setNextBitmap(bitmapCache.getMidBitmap());
        bitmapCache.setMidBitmap(bitmapCache.getPrevBitmap());
        bitmapCache.setPrevBitmap(getBitmapFromPageData(prevPage));
    }

    private void saveNextBitmapCache() {
        bitmapCache.setPrevBitmap(bitmapCache.getMidBitmap());
        bitmapCache.setMidBitmap(bitmapCache.getNextBitmap());
        bitmapCache.setNextBitmap(getBitmapFromPageData(nextPage));
    }

    private Bitmap getBitmapFromPageData(Page page) {
        if (page == null || page.getLinesData() == null)
            return null;
        return BitmapUtils.getPageBitmapWidthLines(page.getPageIndex(), pageNums, page.getLinesData(), txtManager,
                getPageBitmap());
    }

    private Bitmap getPageBitmap() {
        if (bitmapCache.getPageBitmap() == null) {
            bitmapCache.setPageBitmap(BitmapUtils.createBitmapTiledBackground(context.getResources(),
                    txtManager.getConfig().getBackgroundColor(), txtManager.getWidth(),
                    txtManager.getHeight()));
        }
        return bitmapCache.getPageBitmap();
    }

    private void saveBitmapCache() {
        bitmapCache.setPrevBitmap(getBitmapFromPageData(prevPage));
        bitmapCache.setMidBitmap(getBitmapFromPageData(middPage));
        bitmapCache.setNextBitmap(getBitmapFromPageData(nextPage));
    }

    private Boolean isEmpty(Page page) {
        return page.getLinesData() == null || page.getLinesData().size() == 0;
    }

    /**
     * 从开始位置填充pre mid next页，没有数据的页都是null
     *
     * @param firstElementParagraphIndex
     * @param firstElementCharIndex
     * @param pageIndex
     */
    private void loadFromChar(int firstElementParagraphIndex, int firstElementCharIndex, int pageIndex) {
        prevPage = getPageFromPosition(firstElementParagraphIndex, firstElementCharIndex, txtManager.getTextPaint(),
                txtManager.getWidth(), txtManager.getLineSize());
        if (prevPage == null) {
            middPage = null;
            nextPage = null;
            return;
        }
        prevPage.setPageIndex(pageIndex);
        middPage = getPageFromPosition(prevPage.getLastElementParagraphIndex(), prevPage.getLastElementCharIndex(),
                txtManager.getTextPaint(), txtManager.getWidth(), txtManager.getLineSize());
        if (middPage == null) {
            nextPage = null;
            return;
        }
        middPage.setPageIndex(prevPage.getPageIndex() + 1);
        middPage.setFirstPage(false);
        nextPage = getPageFromPosition(middPage.getLastElementParagraphIndex(), middPage.getLastElementCharIndex(),
                txtManager.getTextPaint(), txtManager.getWidth(), txtManager.getLineSize());
        if (nextPage != null) {
            nextPage.setPageIndex(middPage.getPageIndex() + 1);
        }
        saveBitmapCache();
    }

    private void loadFirstPage() {
        loadFromChar(0, -1, 1);
        if (prevPage == null) {
            modeTransformer.onLoadFirstPage(true);
        } else if (middPage == null) {
            middPage = prevPage;
            getBitmapCache().setMidBitmap(getBitmapCache().getPrevBitmap());
            modeTransformer.onLoadFirstPage(true);
        } else {
            nextPage = middPage;
            middPage = prevPage;
            getBitmapCache().setNextBitmap(getBitmapCache().getMidBitmap());
            getBitmapCache().setMidBitmap(getBitmapCache().getPrevBitmap());
            modeTransformer.onLoadFirstPage(false);
        }
    }

    /**
     * 如果传入的开始段落位置超出了数据源本身，返回的是null
     *
     * @param firstElementParagraphIndex
     * @param firstElementCharIndex
     * @param textPaint
     * @param width
     * @param lineSize
     * @return
     */
    private Page getPageFromPosition(int firstElementParagraphIndex, int firstElementCharIndex,
                                     Paint textPaint, int width, int lineSize) {
        int lineWidth = width - txtManager.getConfig().getMarginLeft() - txtManager.getConfig().getMarginRight();
        return txtPipeline.getPageFromPosition(firstElementParagraphIndex, firstElementCharIndex, textPaint, lineWidth, lineSize);
    }

    /**
     * 启动线程进行分页
     */
    public void separateToPages() {
        stopSaparateThread = true;
        pauseSaparateThread = false;
        if (db == null) {
            db = new TxtPageDB(context, TxtPageDB.DBNAME, null, 1);
        }
        synchronized (db) {
            pauseSaparateThread = false;
            db.delectTable();
            db.createTable();
            stopSaparateThread = false;
            pageNums = -1;
            final int paragraphsize = txtPipeline.getParagraphCache().getParagraphSize();
            if (paragraphsize == 0) {
                return;
            }

            new Thread(new Runnable() {

                int lastcharindex = -1;
                int lastpindex = 0;

                @Override
                public void run() {
                    saparateDone = false;
                    modeTransformer.onPageSeparateStart();
                    int index = 1;
                    while (!stopSaparateThread && lastpindex < paragraphsize) {
                        if (!pauseSaparateThread) {
                            Page page = getPageFromPosition(lastpindex, lastcharindex, txtManager.getTextPaint(),
                                    txtManager.getWidth(), txtManager.getLineSize());
                            lastcharindex = page.getLastElementCharIndex();
                            lastpindex = page.getLastElementParagraphIndex();

                            if (index < 100) {
                                bufferCaches.put(index++, page);
                            }

                            if (lastpindex < paragraphsize && !stopSaparateThread) {
                                db.savePage(page.getFirstElementCharIndex(), page.getFirstElementParagraphIndex(),
                                        page.getLastElementCharIndex(), page.getLastElementParagraphIndex());
                            }
                        }
                    }
                    pageNums = db.getLastPageIndex();
                    saparateDone = true;
                    modeTransformer.onPageSeparateDone();
                }
            }).start();
        }
    }

    public String getCharsetName() throws IOException {
        String charsetName = new FileCharsetDetector().guessFileEncoding(txtfile);
        if (charsetName.contains("Windows") || charsetName.contains("WINDOWS")) {
            charsetName = "unicode";
        }
        return charsetName;
    }

    public Page getPrevPage() {
        return prevPage;
    }

    public Page getMiddPage() {
        return middPage;
    }

    public Page getNextPage() {
        return nextPage;
    }

    public int getPageNums() {
        return pageNums;
    }

    public boolean isSaparateDone() {
        return saparateDone;
    }

    private class ManagerTransformerImpl implements ManagerTransformer {
        @Override
        public void loadTxtFile(Transformer transformer) {
            Transformer transformer1 = new Transformer() {
                @Override
                public void postResult(Boolean t) {
                    transformer.postResult(t);
                    if (t) {
                        if (txtPipeline.hasCacheData()) {
                            loadFirstPage();// 加载第一页
                            separateToPages();// 进行分页
                            modeTransformer.refreshView();
                        } else {
                            modeTransformer.onNoData();
                        }
                    } else {
                        modeTransformer.onLoadFileExecption();
                    }
                }

                @Override
                public void postError(TxtError txterror) {
                    transformer.postError(txterror);
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        loadTxtBook(transformer1);
                    } catch (Exception e) {
                        transformer1.postResult(false);
                    }
                }
            }).start();
        }

        @Override
        public void jumpToPage(int pageNumber) {
            loadPage(pageNumber);
        }

        @Override
        public void separatePage() {
            separateToPages();
        }

        @Override
        public void refreshBitmapText() {
            if (modeTransformer != null) {
                saveBitmapCache();
                modeTransformer.refreshView();
            }
        }

        @Override
        public void refreshBitmapBackground() {
            if (modeTransformer != null) {
                refreshPageBitmap();
                saveBitmapCache();
                modeTransformer.refreshView();
            }
        }

        @Override
        public void postResult(Boolean t) {

        }

        @Override
        public void postError(TxtError txterror) {

        }
    }
}