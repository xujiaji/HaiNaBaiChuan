package io.xujiaji.hnbc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.List;

import io.xujiaji.hnbc.model.entity.Post;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJiSuan() {
        String testStr = "  1. dsafdasf";
        System.out.println(testStr.matches("\\s*[1-9]+[0-9]*\\.\\s.+"));
        System.out.println(testStr.substring(0, testStr.indexOf('.')).trim());
    }

    String data = "[{\"author\":{\"__type\":\"Object\",\"age\":0,\"className\":\"_User\",\"createdAt\":\"2017-05-11 19:47:10\",\"followPerson\":{\"__type\":\"Relation\",\"className\":\"_User\"},\"mobilePhoneNumber\":\"15015074234\",\"nickname\":\"神秘人\",\"objectId\":\"0345e935f1\",\"sex\":0,\"updatedAt\":\"2017-05-29 09:35:40\",\"username\":\"111111\"},\"content\":\"111\",\"createdAt\":\"2017-06-02 16:03:25\",\"objectId\":\"6309669b43\",\"title\":\"111\",\"updatedAt\":\"2017-06-02 16:03:25\"},{\"author\":{\"__type\":\"Object\",\"age\":0,\"className\":\"_User\",\"createdAt\":\"2017-05-11 19:47:10\",\"followPerson\":{\"__type\":\"Relation\",\"className\":\"_User\"},\"mobilePhoneNumber\":\"15015074234\",\"nickname\":\"神秘人\",\"objectId\":\"0345e935f1\",\"sex\":0,\"updatedAt\":\"2017-05-29 09:35:40\",\"username\":\"111111\"},\"content\":\"ghjj\",\"createdAt\":\"2017-05-29 16:18:07\",\"objectId\":\"89f09c038e\",\"title\":\"fghh\",\"updatedAt\":\"2017-05-29 16:18:07\"},{\"author\":{\"__type\":\"Object\",\"age\":0,\"className\":\"_User\",\"createdAt\":\"2017-05-11 19:47:10\",\"followPerson\":{\"__type\":\"Relation\",\"className\":\"_User\"},\"mobilePhoneNumber\":\"15015074234\",\"nickname\":\"神秘人\",\"objectId\":\"0345e935f1\",\"sex\":0,\"updatedAt\":\"2017-05-29 09:35:40\",\"username\":\"111111\"},\"content\":\"111\",\"createdAt\":\"2017-05-29 14:12:44\",\"objectId\":\"0ab2e154da\",\"title\":\"111\",\"updatedAt\":\"2017-05-29 14:12:44\"},{\"author\":{\"__type\":\"Object\",\"age\":0,\"className\":\"_User\",\"createdAt\":\"2017-05-11 19:47:10\",\"followPerson\":{\"__type\":\"Relation\",\"className\":\"_User\"},\"mobilePhoneNumber\":\"15015074234\",\"nickname\":\"神秘人\",\"objectId\":\"0345e935f1\",\"sex\":0,\"updatedAt\":\"2017-05-29 09:35:40\",\"username\":\"111111\"},\"content\":\"111\",\"createdAt\":\"2017-05-29 14:12:22\",\"objectId\":\"b47cf7cc24\",\"title\":\"111\",\"updatedAt\":\"2017-05-29 14:12:22\"},{\"author\":{\"__type\":\"Object\",\"age\":0,\"authData\":{\"qq\":{\"access_token\":\"F6716412133DA5F8F1803FCBB9C9B05D\",\"expires_in\":7776000,\"openid\":\"aa2f4d8f50f4f4c33caafc223902b7e9\"}},\"birthday\":\"\",\"city\":\"重庆\",\"className\":\"_User\",\"createdAt\":\"2017-05-22 18:31:11\",\"followPerson\":{\"__type\":\"Relation\",\"className\":\"_User\"},\"headPic\":\"http:\\/\\/bmob-cdn-7358.b0.upaiyun.com\\/2017\\/05\\/22\\/2e0bebb35114434e98c980e9e18737ea.bmob\\/cropped\",\"nickname\":\"倩少爷\",\"objectId\":\"32d7ff937a\",\"sex\":0,\"updatedAt\":\"2017-06-04 10:39:38\",\"username\":\"117d31e459fe8260\"},\"content\":\"谁能告诉本少爷\",\"createdAt\":\"2017-05-22 18:35:07\",\"objectId\":\"4f0e7ebc86\",\"title\":\"这是什么软件\",\"updatedAt\":\"2017-05-22 18:35:07\"}]";

    String data2 = "[{\n" +
            "        \"author\": {\n" +
            "            \"__type\": \"Object\",\n" +
            "            \"age\": 0,\n" +
            "            \"className\": \"_User\",\n" +
            "            \"createdAt\": \"2017-05-11 19:47:10\",\n" +
            "            \"followPerson\": {\n" +
            "                \"__type\": \"Relation\",\n" +
            "                \"className\": \"_User\"\n" +
            "            },\n" +
            "            \"mobilePhoneNumber\": \"15015074234\",\n" +
            "            \"nickname\": \"神秘人\",\n" +
            "            \"objectId\": \"0345e935f1\",\n" +
            "            \"sex\": 0,\n" +
            "            \"updatedAt\": \"2017-05-29 09:35:40\",\n" +
            "            \"username\": \"111111\"\n" +
            "        },\n" +
            "        \"content\": \"111\",\n" +
            "        \"createdAt\": \"2017-06-02 16:03:25\",\n" +
            "        \"objectId\": \"6309669b43\",\n" +
            "        \"title\": \"111\",\n" +
            "        \"updatedAt\": \"2017-06-02 16:03:25\"\n" +
            "    }]";
    String data3 = "[{\n" +
            "        \"author\": {\n" +
            "            \"__type\": \"Object\",\n" +
            "            \"age\": 0,\n" +
            "            \"className\": \"_User\",\n" +
            "            \"createdAt\": \"2017-05-11 19:47:10\",\n" +
            "            \"followPerson\": {\n" +
            "                \"__type\": \"Relation\",\n" +
            "                \"className\": \"_User\"\n" +
            "            },\n" +
            "            \"mobilePhoneNumber\": \"15015074234\",\n" +
            "            \"nickname\": \"神秘人\",\n" +
            "            \"objectId\": \"0345e935f1\",\n" +
            "            \"sex\": 0,\n" +
            "            \"updatedAt\": \"2017-05-29 09:35:40\",\n" +
            "            \"username\": \"111111\"\n" +
            "        },\n" +
            "        \"content\": \"ghjj\",\n" +
            "        \"createdAt\": \"2017-05-29 16:18:07\",\n" +
            "        \"objectId\": \"89f09c038e\",\n" +
            "        \"title\": \"fghh\",\n" +
            "        \"updatedAt\": \"2017-05-29 16:18:07\"\n" +
            "    }]";
    String data4 = "[{\n" +
            "        \"author\": {\n" +
            "            \"__type\": \"Object\",\n" +
            "            \"age\": 0,\n" +
            "            \"className\": \"_User\",\n" +
            "            \"createdAt\": \"2017-05-11 19:47:10\",\n" +
            "            \"followPerson\": {\n" +
            "                \"__type\": \"Relation\",\n" +
            "                \"className\": \"_User\"\n" +
            "            },\n" +
            "            \"mobilePhoneNumber\": \"15015074234\",\n" +
            "            \"nickname\": \"神秘人\",\n" +
            "            \"objectId\": \"0345e935f1\",\n" +
            "            \"sex\": 0,\n" +
            "            \"updatedAt\": \"2017-05-29 09:35:40\",\n" +
            "            \"username\": \"111111\"\n" +
            "        },\n" +
            "        \"content\": \"111\",\n" +
            "        \"createdAt\": \"2017-05-29 14:12:44\",\n" +
            "        \"objectId\": \"0ab2e154da\",\n" +
            "        \"title\": \"111\",\n" +
            "        \"updatedAt\": \"2017-05-29 14:12:44\"\n" +
            "    }]";
    String data5 = "[{\n" +
            "        \"author\": {\n" +
            "            \"__type\": \"Object\",\n" +
            "            \"age\": 0,\n" +
            "            \"className\": \"_User\",\n" +
            "            \"createdAt\": \"2017-05-11 19:47:10\",\n" +
            "            \"followPerson\": {\n" +
            "                \"__type\": \"Relation\",\n" +
            "                \"className\": \"_User\"\n" +
            "            },\n" +
            "            \"mobilePhoneNumber\": \"15015074234\",\n" +
            "            \"nickname\": \"神秘人\",\n" +
            "            \"objectId\": \"0345e935f1\",\n" +
            "            \"sex\": 0,\n" +
            "            \"updatedAt\": \"2017-05-29 09:35:40\",\n" +
            "            \"username\": \"111111\"\n" +
            "        },\n" +
            "        \"content\": \"111\",\n" +
            "        \"createdAt\": \"2017-05-29 14:12:22\",\n" +
            "        \"objectId\": \"b47cf7cc24\",\n" +
            "        \"title\": \"111\",\n" +
            "        \"updatedAt\": \"2017-05-29 14:12:22\"\n" +
            "    }]";
    String data6 = "[{\n" +
            "        \"author\": {\n" +
            "            \"__type\": \"Object\",\n" +
            "            \"age\": 0,\n" +
            "            \"authData\": {\n" +
            "                \"qq\": {\n" +
            "                    \"access_token\": \"F6716412133DA5F8F1803FCBB9C9B05D\",\n" +
            "                    \"expires_in\": 7776000,\n" +
            "                    \"openid\": \"aa2f4d8f50f4f4c33caafc223902b7e9\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"birthday\": \"Sep 28, 2018 00:00:00\",\n" +
            "            \"city\": \"重庆\",\n" +
            "            \"className\": \"_User\",\n" +
            "            \"createdAt\": \"2017-05-22 18:31:11\",\n" +
            "            \"followPerson\": {\n" +
            "                \"__type\": \"Relation\",\n" +
            "                \"className\": \"_User\"\n" +
            "            },\n" +
            "            \"headPic\": \"http://bmob-cdn-7358.b0.upaiyun.com/2017/05/22/2e0bebb35114434e98c980e9e18737ea.bmob/cropped\",\n" +
            "            \"nickname\": \"倩少爷\",\n" +
            "            \"objectId\": \"32d7ff937a\",\n" +
            "            \"sex\": 0,\n" +
            "            \"updatedAt\": \"2017-05-22 18:34:42\",\n" +
            "            \"username\": \"117d31e459fe8260\"\n" +
            "        },\n" +
            "        \"content\": \"谁能告诉本少爷\",\n" +
            "        \"createdAt\": \"2017-05-22 18:35:07\",\n" +
            "        \"objectId\": \"4f0e7ebc86\",\n" +
            "        \"title\": \"这是什么软件\",\n" +
            "        \"updatedAt\": \"2017-05-22 18:35:07\"\n" +
            "    }]";
    @Test
    public void testJson() {
        testJson(data);
//        testJson(data2);
//        testJson(data3);
//        testJson(data4);
//        testJson(data5);
//        testJson(data6);
    }

    public void testJson(String d) {
        Gson gson = new Gson();
        List<Post> posts = gson.fromJson(d,  new TypeToken<List<Post>>() {}.getType());
        System.out.println(posts);
    }
}