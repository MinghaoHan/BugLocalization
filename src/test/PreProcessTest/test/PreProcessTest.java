package PreProcessTest.test;

import PreProcess.PreProcess;
import PreProcess.ReadFromFile;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PreProcessTest {

    public PreProcessTest(){
        List<String> keyWordsList = new ArrayList<String>();
        List<String> stopWordsList = new ArrayList<String>();
        ReadFromFile.readFileByLines("src/main/resources/keyword",keyWordsList);
        ReadFromFile.readFileByLines("src/main/resources/stopword",stopWordsList);
        PreProcess.setKeyWordsList(keyWordsList);
        PreProcess.setStopWordsList(stopWordsList);
    }
    @Test
    public void removeKeyWordsTest(){



        String comments = "public statement int type abc";
        String result = PreProcess.removeKeyWords(comments);
        Assert.assertEquals("statement type abc",result);
    }
    @Test
    public void removeStopAndKeyWordTest(){
        String comments1 = "public abstract class AccessibleAdapter implements AccessibleListener {\n" +
                "\n" +
                "\t/**\n" +
                "\t * Sent when an accessibility client requests the name\n" +
                "\t * of the control, or the name of a child of the control.\n" +
                "\t * The default behavior is to do nothing.\n" +
                "\t * <p>\n" +
                "\t * Return the name of the control or specified child in the\n" +
                "\t * <code>result</code> field of the event object. Returning\n" +
                "\t * an empty string tells the client that the control or child\n" +
                "\t * does not have a name, and returning null tells the client\n" +
                "\t * to use the platform name.\n" +
                "\t * </p>\n" +
                "\t *\n" +
                "\t * @param e an event object containing the following fields:<ul>\n" +
                "\t *    <li>childID [IN] - an identifier specifying the control or one of its children</li>\n" +
                "\t *    <li>result [OUT] - the requested name string, or null</li>\n" +
                "\t * </ul>\n" +
                "\t */package org.eclipse.swt.dnd;\n" +
                "\n" +
                "import org.eclipse.swt.widgets.Event;\n" +
                "\n" +
                "class DNDEvent extends Event {\n" +
                "    public TransferData dataType;\n" +
                "    public TransferData[] dataTypes;\n" +
                "    public int operations;\n" +
                "    public int feedback;\n" +
                "\n" +
                "    DNDEvent() {\n" +
                "    }\n" +
                "}";
        String result1 = PreProcess.removeStopWords(comments1);
        String expectedString1 = "public abstract class AccessibleAdapter implements AccessibleListener package org eclipse swt dnd import org eclipse swt widgets Event class DNDEvent extends Event public TransferData dataType public TransferData dataTypes public int operations public int feedback DNDEvent";
        Assert.assertEquals(expectedString1,result1);
        String result2 = PreProcess.removeKeyWords(result1);
        String expectedString2 = "AccessibleAdapter AccessibleListener org eclipse swt dnd org eclipse swt widgets Event DNDEvent Event TransferData dataType TransferData dataTypes operations feedback DNDEvent";
        Assert.assertEquals(expectedString2,result2);
    }
    @Test
    public void stemTest(){
        String comments = "delegation declaration";
        String result = PreProcess.stemming(comments);
        Assert.assertEquals("deleg declar",result);
    }
    @Test
    public void stemmingAndlemmaTest(){
        String comments = "does was files listeners";
        String result = PreProcess.lemmatisation(comments);
        String re = PreProcess.stemming(result);
        Assert.assertEquals("do be file listen",re);
    }
    @Test
    public void splitterTest(){
        String comments = "Typedeclaration styleRange lovelive";
        String result = PreProcess.splitter(comments);
        Assert.assertEquals("Type declaration style Range love live",result);
    }


}
