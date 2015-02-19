package io;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class FileNameFormatUtilTest {



    @Test
    public void testCheckIfPathAbsolute_AbsolutePathTest() throws Exception {
        FileNameFormatUtil util = new FileNameFormatUtil();
        List<String> data = new LinkedList<>();
        data.add("C:\\Users\\Spider\\IdeaProjects\\service-system/results/u300p10/simple-constants/reputationV/reputations.txt");
        data.add("C:/Users/Spider/IdeaProjects/service-system/results/u300p10/simple-constants/reputationV/reputations.txt");
        data.add(System.getProperty("user.dir"));

        boolean result;

        for (String tmp : data) {
            result = util.checkIfPathAbsolute(tmp);
            Assert.assertEquals(true, result);
        }

    }

    @Test
    public void testCheckIfPathAbsolute_NotAbsolutePathTest() throws Exception {
        FileNameFormatUtil util = new FileNameFormatUtil();
        List<String> data = new LinkedList<>();
        data.add("/Users/Spider/IdeaProjects/service-system/results/u300p10/simple-constants/reputationV/reputations.txt");
        data.add("/Spider/IdeaProjects/service-system/results/u300p10/simple-constants/reputationV/reputations.txt");
        data.add(System.getProperty("user.dir").replaceAll(":",""));
        data.add("reputations.txt");

        boolean result;

        for (String tmp : data) {
            result = util.checkIfPathAbsolute(tmp);
            Assert.assertEquals(false, result);
        }
    }

}