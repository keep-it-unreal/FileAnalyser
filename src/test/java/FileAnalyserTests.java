import fileManager.FileAnalyser;
import fileManager.FileAnalyserImpl;
import org.junit.Assert;
import org.junit.Test;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileAnalyserTests {

    private FileAnalyser fileAnalyser = null;

    /**
     * Этот unit-тест проверяет корректность метода getFileName(). Указанный метод возвращает
     * наименование файла.
     * После завершения работы метода сравниваем получившиеся значения с искомыми.
     */
    @Test
    public void getFileNameTest() throws IOException {
        fileAnalyser = new FileAnalyserImpl("src/test/java/input.txt");
        Assert.assertEquals("input.txt", fileAnalyser.getFileName());
    }
    /**
     * Этот unit-тест проверяет корректность метода getStatisticTest(). Указанный метод не возвращает
     * значения. Результатом выполнения метода является определение количества строк в тексте, а также
     * определения количества каждого из символов.
     * После получения завершения работы метода сравниваем получившиеся значения с искомыми.
     */
    @Test
    public void getStatisticTest() throws IOException {
        fileAnalyser = new FileAnalyserImpl("src/test/java/input.txt");
        ((FileAnalyserImpl)fileAnalyser).getStatistic();
        Assert.assertEquals(9, fileAnalyser.getRowsCount());
        Assert.assertEquals(10, (Object)fileAnalyser.getSymbolsStatistics().get('M'));
    }
    /**
     * Этот unit-тест проверяет корректность метода getTopNPopularSymbols(int n). Указанный метод
     * возвращает указанное количество самых популярных символов в порядке убывания.
     * После завершения работы метода сравниваем получившееся значение с искомым.
     */
    @Test
    public void getTopNLettersTests() throws IOException {
        fileAnalyser = new FileAnalyserImpl("src/test/java/input.txt");
        ((FileAnalyserImpl)fileAnalyser).getStatistic();
        Assert.assertEquals('a', ((ArrayList)fileAnalyser.getTopNPopularSymbols(5)).get(4));
    }

    /**
     * Этот unit-тест проверяет работу класса (конструктора) на альтернативный сценарий (некорректно
     * введенный путь к файлу).
     */
    @Test
    public void incorrectFileNameTest() {
        try{
            fileAnalyser = new FileAnalyserImpl("src/test/java/inpAt.txt");
            throw new IOException();
        } catch (IOException e){}
    }
}