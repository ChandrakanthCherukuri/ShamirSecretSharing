import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class SecretFinder {

    static class Point {
        BigInteger x;
        BigInteger y;

        public Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("resources/input.json")));
            JSONObject json = new JSONObject(content);
            JSONArray testcases = json.getJSONArray("testcases");

            for (int t = 0; t < testcases.length(); t++) {
                JSONObject test = testcases.getJSONObject(t);
                int n = test.getInt("n");
                int k = test.getInt("k");
                JSONArray shares = test.getJSONArray("shares");

                List<Point> points = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    JSONObject share = shares.getJSONObject(i);
                    BigInteger x = new BigInteger(share.get("key").toString());
                    String value = share.getString("value");
                    int base = share.getInt("base");
                    BigInteger y = new BigInteger(value, base);
                    points.add(new Point(x, y));
                }

                Map<BigInteger, Integer> countSecrets = new HashMap<>();
                int[] indices = IntStream.range(0, n).toArray();
                generateCombinations(indices, k, 0, new int[k], points, countSecrets);

                BigInteger secret = findMostFrequent(countSecrets);
                System.out.println("Secret for Testcase " + (t + 1) + ": " + secret);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static BigInteger lagrangeInterpolation(List<Point> points) {
        BigInteger secret = BigInteger.ZERO;
        int k = points.size();
        for (int j = 0; j < k; j++) {
            Point pj = points.get(j);
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int i = 0; i < k; i++) {
                if (i != j) {
                    Point pi = points.get(i);
                    num = num.multiply(pi.x.negate());
                    den = den.multiply(pj.x.subtract(pi.x));
                }
            }

            BigInteger term = pj.y.multiply(num).divide(den);
            secret = secret.add(term);
        }
        return secret;
    }

    private static void generateCombinations(int[] indices, int k, int start, int[] combo, List<Point> points, Map<BigInteger, Integer> map) {
        if (k == 0) {
            List<Point> subset = new ArrayList<>();
            for (int index : combo) subset.add(points.get(index));
            try {
                BigInteger secret = lagrangeInterpolation(subset);
                map.put(secret, map.getOrDefault(secret, 0) + 1);
            } catch (Exception ignored) {}
            return;
        }
        for (int i = start; i <= indices.length - k; i++) {
            combo[combo.length - k] = indices[i];
            generateCombinations(indices, k - 1, i + 1, combo, points, map);
        }
    }

    private static BigInteger findMostFrequent(Map<BigInteger, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(BigInteger.ZERO);
    }
}
