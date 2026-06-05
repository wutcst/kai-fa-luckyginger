/**
 * JsonUtil类的单元测试用例。
 * 测试JSON序列化和反序列化功能，包括Map转JSON、特殊字符转义、简单JSON解析等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class JsonUtilTest
{
    /**
     * 运行所有JsonUtil类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("JsonUtil类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testToJsonNullMap()) {
            System.out.println("✅ 测试1: null Map转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: null Map转JSON - 失败");
            failed++;
        }

        if (testToJsonEmptyMap()) {
            System.out.println("✅ 测试2: 空Map转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 空Map转JSON - 失败");
            failed++;
        }

        if (testToJsonSingleEntry()) {
            System.out.println("✅ 测试3: 单条目Map转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 单条目Map转JSON - 失败");
            failed++;
        }

        if (testToJsonMultipleEntries()) {
            System.out.println("✅ 测试4: 多条目Map转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 多条目Map转JSON - 失败");
            failed++;
        }

        if (testToJsonStringValue()) {
            System.out.println("✅ 测试5: 字符串值转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 字符串值转JSON - 失败");
            failed++;
        }

        if (testToJsonNumberValue()) {
            System.out.println("✅ 测试6: 数值类型转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 数值类型转JSON - 失败");
            failed++;
        }

        if (testToJsonBooleanValue()) {
            System.out.println("✅ 测试7: 布尔值转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 布尔值转JSON - 失败");
            failed++;
        }

        if (testToJsonNullValue()) {
            System.out.println("✅ 测试8: null值转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: null值转JSON - 失败");
            failed++;
        }

        if (testToJsonNestedMap()) {
            System.out.println("✅ 测试9: 嵌套Map转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 嵌套Map转JSON - 失败");
            failed++;
        }

        if (testToJsonListValue()) {
            System.out.println("✅ 测试10: List值转JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: List值转JSON - 失败");
            failed++;
        }

        if (testEscapeDoubleQuote()) {
            System.out.println("✅ 测试11: 双引号转义 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 双引号转义 - 失败");
            failed++;
        }

        if (testEscapeBackslash()) {
            System.out.println("✅ 测试12: 反斜杠转义 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 反斜杠转义 - 失败");
            failed++;
        }

        if (testEscapeNewline()) {
            System.out.println("✅ 测试13: 换行符转义 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 换行符转义 - 失败");
            failed++;
        }

        if (testEscapeTab()) {
            System.out.println("✅ 测试14: 制表符转义 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 制表符转义 - 失败");
            failed++;
        }

        if (testParseSimpleJsonEmpty()) {
            System.out.println("✅ 测试15: 解析空JSON - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 解析空JSON - 失败");
            failed++;
        }

        if (testParseSimpleJsonSinglePair()) {
            System.out.println("✅ 测试16: 解析单键值对 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 解析单键值对 - 失败");
            failed++;
        }

        if (testParseSimpleJsonMultiplePairs()) {
            System.out.println("✅ 测试17: 解析多键值对 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: 解析多键值对 - 失败");
            failed++;
        }

        if (testParseSimpleJsonNull()) {
            System.out.println("✅ 测试18: 解析null输入 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 解析null输入 - 失败");
            failed++;
        }

        if (testParseSimpleJsonEmptyString()) {
            System.out.println("✅ 测试19: 解析空字符串 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试19: 解析空字符串 - 失败");
            failed++;
        }

        if (testRoundTrip()) {
            System.out.println("✅ 测试20: 序列化反序列化往返测试 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试20: 序列化反序列化往返测试 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testToJsonNullMap()
    {
        try {
            String result = JsonUtil.toJson(null);
            if (!result.equals("null")) {
                System.out.println("  错误: null Map应返回字符串'null'，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonEmptyMap()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            String result = JsonUtil.toJson(map);
            if (!result.equals("{}")) {
                System.out.println("  错误: 空Map应返回'{}'，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonSingleEntry()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "test");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\"name\"") || !result.contains("\"test\"")) {
                System.out.println("  错误: 单条目Map结果不包含预期的键值对，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonMultipleEntries()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "test");
            map.put("value", "hello");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\"name\"") || !result.contains("\"value\"")) {
                System.out.println("  错误: 多条目Map结果不包含所有键，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonStringValue()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "hello world");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\"hello world\"")) {
                System.out.println("  错误: 字符串值应被引号包裹，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonNumberValue()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("count", 42);
            map.put("price", 3.14);
            String result = JsonUtil.toJson(map);
            if (!result.contains("42")) {
                System.out.println("  错误: 整数值应直接输出，实际: " + result);
                return false;
            }
            if (!result.contains("3.14")) {
                System.out.println("  错误: 浮点数值应直接输出，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonBooleanValue()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("active", true);
            map.put("deleted", false);
            String result = JsonUtil.toJson(map);
            if (!result.contains("true") || !result.contains("false")) {
                System.out.println("  错误: 布尔值应直接输出，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonNullValue()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("data", null);
            String result = JsonUtil.toJson(map);
            if (!result.contains("null")) {
                System.out.println("  错误: null值应输出为null，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonNestedMap()
    {
        try {
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("inner_key", "inner_value");
            Map<String, Object> outerMap = new HashMap<>();
            outerMap.put("nested", innerMap);
            String result = JsonUtil.toJson(outerMap);
            if (!result.contains("\"inner_key\"") || !result.contains("\"inner_value\"")) {
                System.out.println("  错误: 嵌套Map应正确序列化，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToJsonListValue()
    {
        try {
            List<Object> list = new ArrayList<>();
            list.add("item1");
            list.add("item2");
            Map<String, Object> map = new HashMap<>();
            map.put("items", list);
            String result = JsonUtil.toJson(map);
            if (!result.contains("[") || !result.contains("]")) {
                System.out.println("  错误: List应序列化为JSON数组，实际: " + result);
                return false;
            }
            if (!result.contains("\"item1\"") || !result.contains("\"item2\"")) {
                System.out.println("  错误: List元素应正确序列化，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEscapeDoubleQuote()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("text", "say \"hello\"");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\\\"")) {
                System.out.println("  错误: 双引号应被转义，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEscapeBackslash()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("path", "C:\\Users\\test");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\\\\")) {
                System.out.println("  错误: 反斜杠应被转义，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEscapeNewline()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("text", "line1\nline2");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\\n")) {
                System.out.println("  错误: 换行符应被转义为\\n，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEscapeTab()
    {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("text", "col1\tcol2");
            String result = JsonUtil.toJson(map);
            if (!result.contains("\\t")) {
                System.out.println("  错误: 制表符应被转义为\\t，实际: " + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSimpleJsonEmpty()
    {
        try {
            Map<String, String> result = JsonUtil.parseSimpleJson("{}");
            if (result == null || !result.isEmpty()) {
                System.out.println("  错误: 空JSON对象应返回空Map");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSimpleJsonSinglePair()
    {
        try {
            Map<String, String> result = JsonUtil.parseSimpleJson("{\"name\": \"test\"}");
            if (result.size() != 1) {
                System.out.println("  错误: 单键值对JSON应返回大小为1的Map，实际: " + result.size());
                return false;
            }
            if (!result.get("name").equals("test")) {
                System.out.println("  错误: name值应为test，实际: " + result.get("name"));
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSimpleJsonMultiplePairs()
    {
        try {
            Map<String, String> result = JsonUtil.parseSimpleJson("{\"name\": \"test\", \"value\": \"hello\"}");
            if (result.size() != 2) {
                System.out.println("  错误: 双键值对JSON应返回大小为2的Map，实际: " + result.size());
                return false;
            }
            if (!result.get("name").equals("test")) {
                System.out.println("  错误: name值应为test");
                return false;
            }
            if (!result.get("value").equals("hello")) {
                System.out.println("  错误: value值应为hello");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSimpleJsonNull()
    {
        try {
            Map<String, String> result = JsonUtil.parseSimpleJson(null);
            if (result == null || !result.isEmpty()) {
                System.out.println("  错误: null输入应返回空Map");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSimpleJsonEmptyString()
    {
        try {
            Map<String, String> result = JsonUtil.parseSimpleJson("");
            if (result == null || !result.isEmpty()) {
                System.out.println("  错误: 空字符串应返回空Map");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoundTrip()
    {
        try {
            Map<String, Object> original = new HashMap<>();
            original.put("name", "zuul");
            original.put("version", 2);
            original.put("active", true);
            String json = JsonUtil.toJson(original);
            Map<String, String> parsed = JsonUtil.parseSimpleJson(json);
            if (!parsed.get("name").equals("zuul")) {
                System.out.println("  错误: 往返测试name值不匹配");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
