package com.hdmb.ireader.txtreader.pipeline;

import android.graphics.Paint;

import com.hdmb.ireader.txtreader.Transformer;
import com.hdmb.ireader.txtreader.TxtErrorCode;
import com.hdmb.ireader.txtreader.bean.CharElement;
import com.hdmb.ireader.txtreader.bean.LineChar;
import com.hdmb.ireader.txtreader.bean.LineCharImpl;
import com.hdmb.ireader.txtreader.bean.Page;
import com.hdmb.ireader.txtreader.bean.Paragraph;
import com.hdmb.ireader.txtreader.bean.ParagraphCache;
import com.hdmb.ireader.txtreader.bean.TxtError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:56
 * 文件    TbReader
 * 描述    这是对字符数据的处理类,需要传入一个txt文件，并维持一个段落缓存
 */
public class TxtPipeline {

    private ParagraphCache paragraphCache;

    public TxtPipeline() {
        paragraphCache = new ParagraphCache();
    }

    public ParagraphCache getParagraphCache() {
        return paragraphCache;
    }

    public boolean hasCacheData() {
        return paragraphCache.hasParagraphCache();
    }

    /**
     * 加载txt文件，返回段落集合缓存
     *
     * @param txtFile
     * @param charsetName
     * @param transformer
     * @return
     */
    public ParagraphCache loadTxtFile(File txtFile, String charsetName, Transformer transformer) {
        BufferedReader bufferedReader = null;
        TxtError txtError = new TxtError();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), charsetName));
        } catch (FileNotFoundException e) {
            txtError.setTxtErrorCode(TxtErrorCode.LOAD_BOOK_EXCEPTION);
            txtError.setTxtErrorMsg("无法找到该书籍文件");
            transformer.postResult(false);
            transformer.postError(txtError);
            return paragraphCache;
        } catch (UnsupportedEncodingException e) {
            txtError.setTxtErrorCode(TxtErrorCode.LOAD_BOOK_EXCEPTION);
            txtError.setTxtErrorMsg("读取文件编码失败");
            transformer.postResult(false);
            transformer.postError(txtError);
            return paragraphCache;
        }
        String data;
        int index = 0;
        try {
            while ((data = bufferedReader.readLine()) != null) {
                Paragraph paragraph = new Paragraph();
                paragraph.setIndex(index);
                paragraph.setData(data);
                index++;
                paragraphCache.addParagraph(paragraph);
            }
        } catch (IOException e) {
            txtError.setTxtErrorCode(TxtErrorCode.LOAD_BOOK_EXCEPTION);
            txtError.setTxtErrorMsg("加载书籍时出现io异常");
            transformer.postResult(false);
            transformer.postError(txtError);
            return paragraphCache;
        }
        transformer.postResult(true);
        return paragraphCache;
    }

    /**
     * 将一段段的段落转化为行数据，然后根据一页显示多少行进行填充，如果没数据返回空
     *
     * @param startParagraphIndex
     * @param startCharIndex
     * @param paint
     * @param lineWidth
     * @param lineSize
     * @return
     */
    public Page getPageFromPosition(int startParagraphIndex, int startCharIndex,
                                    Paint paint, float lineWidth, int lineSize) {
        Page page = new Page();
        page.setFirstElementParagraphIndex(startParagraphIndex);
        page.setFirstElementCharIndex(startCharIndex);

        int paragraphCacheSize = getParagraphCache().getParagraphSize();

        List<LineChar> lineChars = new ArrayList<>();

        Paragraph paragraph = getParagraphCache().getParagraphByIndex(startParagraphIndex);
        if (paragraph == null)
            return null;

        int length = paragraph.getData().length();
        // 如果游标开始的话，开始应该上一个位置的下一个字符，如果是段落的结尾应该换下段落，否则就是下个字符
        if (startCharIndex == length - 1) {
            startCharIndex = 0;
            startParagraphIndex++;
        } else {
            startCharIndex++;
        }
        List<LineChar> list = getLinesFromParagraph(startParagraphIndex, startCharIndex, paint, lineWidth);
        if (list.size() > 0)
            startCharIndex = 0;
        lineChars.addAll(list);

        startParagraphIndex++;
        // 如果已经填充的行小于每页的行数，并且段落的下表小于所有段落的数量
        while (lineChars.size() < lineSize && startParagraphIndex < paragraphCacheSize) {
            list = getLinesFromParagraph(startParagraphIndex, startCharIndex, paint, lineWidth);
            lineChars.addAll(list);
            startParagraphIndex++;
        }
        if (lineChars.size() > 0) {
            // 添加的行数超出的话应该把超出的去掉
            if (lineChars.size() > lineSize) {
                while (lineChars.size() > lineSize)
                    lineChars.remove(lineChars.size() - 1);
            }

            // 获取最后一行，并标志最后字符位置
            LineChar lineChar = lineChars.get(lineChars.size() - 1);
            if (lineChar.hasData()) {
                page.setLastElementParagraphIndex(lineChar.getLastElement().paragraphIndex);
                page.setLastElementCharIndex(lineChar.getLastElement().charIndex);
            } else {
                page.setLastElementParagraphIndex(startParagraphIndex + 1);
                page.setLastElementCharIndex(0);
            }
            page.setLinesData(lineChars);
        } else {// 没有数据
            page.setLastElementParagraphIndex(startParagraphIndex + 1);
            page.setLastElementCharIndex(0);
        }
        return page;
    }

    /**
     * 根据一个段落的起始位置算出多行数据
     *
     * @param startParagraphIndex
     * @param startCharIndex
     * @param paint
     * @param lineWidth
     * @return
     */
    private List<LineChar> getLinesFromParagraph(int startParagraphIndex, int startCharIndex, Paint paint, float lineWidth) {
        List<LineChar> lineChars = new ArrayList<>();
        Paragraph paragraph = paragraphCache.getParagraphByIndex(startParagraphIndex);
        if (paragraph == null)
            return new ArrayList<>();
        String paragraphStr = paragraph.getData();
        int length = paragraphStr.length();

        if (length > 0 && length > startCharIndex) {
            paragraphStr = paragraphStr.substring(startCharIndex, length);
            while (paragraphStr.length() > 0) {
                int size = paint.breakText(paragraphStr, true, lineWidth, null);
                if (size <= paragraphStr.length()) {
                    LineChar lineChar = getLineCharFromString(paragraphStr.substring(0, size), paragraph.getIndex(), startCharIndex);
                    lineChars.add(lineChar);
                    paragraphStr = paragraphStr.substring(size, paragraphStr.length());
                    startCharIndex = startCharIndex + size;
                } else {
                    LineChar lineChar = getLineCharFromString(paragraphStr, paragraph.getIndex(), startCharIndex);
                    lineChars.add(lineChar);
                    paragraphStr = "";
                }
            }
        }
        return lineChars;
    }

    /**
     * 从字符串中获取一行的数据
     *
     * @param data
     * @param paragraphIndex
     * @param startCharIndex
     * @return
     */
    public LineChar getLineCharFromString(String data, int paragraphIndex, int startCharIndex) {
        LineChar lineChar = new LineCharImpl();
        char[] cs = data.toCharArray();
        int clength = cs.length;
        if (clength > 0) {
            for (int i = 0; i < clength; i++) {
                CharElement charElement = new CharElement();
                charElement.paragraphIndex = paragraphIndex;
                charElement.data = cs[i];
                charElement.charIndex = startCharIndex + i;
                lineChar.addElement(charElement);
            }
        }
        return lineChar;
    }

}
