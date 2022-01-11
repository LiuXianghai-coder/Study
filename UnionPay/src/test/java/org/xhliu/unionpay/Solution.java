package org.xhliu.unionpay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Solution {
    List<Integer> ans = new LinkedList<>();
    int n;
    int max;

    public List<Integer> grayCode(int n) {
        this.n = n;
        max = 1 << n;
        ans.add(0);
        for (int i = 1; i < max; ++i) dfs(i);

        return ans;
    }

    private void dfs(int cur) {
        if (cur >= max) return;
        if (ans.contains(cur)) return;

        if (ans.size() == max - 1) {
            if (diff(0, cur) != 1) return;
            ans.add(cur);
            return;
        }

        ans.add(cur);
        for (int i = 1; i < max; ++i) {
            if (ans.contains(i)) continue;
            if (diff(cur, i) != 1) continue;
            dfs(i);
             System.out.println("next=" + i + "\tans size=" + ans.size());
            if (ans.size() == max) return;
        }
        ans.remove(Integer.valueOf(cur));
    }

    public Map<String, Integer> wordCount(List<String> words) {
        Map<String, Integer> ans = new HashMap<>();
        words.forEach(word -> ans.put(word, ans.getOrDefault(word, 0) + 1));
        return ans;
    }

    int diff(int a, int b) {
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            int a1 = (a >> i) & 1;
            int b1 = (b >> i) & 1;
            if (a1 != b1) cnt++;
        }

        return cnt;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Solution solution = new Solution();
//        System.out.println(solution.wordCount(Arrays.asList("cat", "tom", "jerry", "spark", "tom", "cat")));
        MessageDigest md = MessageDigest.getInstance("MD5");
        String privateKey = "b3BlbnNzaC1rZXktdjEAAAAACmFlczI1Ni1jdHIAAAAGYmNyeXB0AAAAGAAAABDI5VemnC\n" +
                "ElKj6nvrD/wl/GAAAAEAAAAAEAAAGXAAAAB3NzaC1yc2EAAAADAQABAAABgQC6ajOllyZu\n" +
                "mvFp88l5SNUvg6lU8DgMX2hXD4CsCmUCyLR0vkha6OWw1u1FYrJ8bTJfcVDu8wnKKP4q+7\n" +
                "S9C0p3/ff5LS8c6V7FslA1z3eyJraTpq2Dk4Ztl5rpHT0z5idRkf4Byjbymw4GNSRBy6Fk\n" +
                "qQBilkos5hMp5hRYbgIuCul9hvRsYTGOyJsMgxadCOmKL0q9xilrTJe7uRakov88+qT5LT\n" +
                "kztdbB0xwLnhR/DIXcG3U7rV0Bh+8vSq/GZNVcx/aAThGqCzBjSL+Uv+Lf4U/80XxMK9xx\n" +
                "o/c3a1e4YDi6vQ1/54LrLg0C32LkT7Y+OMhREAVVU/dnIfxPpilQg8apAgsn7vWanP/58z\n" +
                "aWPjXmMvrAVwdoNANHzJPZSRU5pb5cuNswoOPM8V7WtxXG6Y+ZPsPa7lLCDCkVuUp/lAoJ\n" +
                "tCgD7eZ7Eyk7RcV380cjz5wGlqGhfGuLKdnpGAH5r12aJoo8WSndzi38UXnFQlxuft2FbQ\n" +
                "GpW5NhfpvxPdsAAAWQppuOq/7bSYpAUMlzKEJAAIFW7eUcsQ/U3WOdtX3vukGlMjhuG3gK\n" +
                "mwQC+klhv7GUM0yaQZVbLgHAntk2IgaarSr1gR/C3O6mRaNqWR+G5wmechM5/BaiXiBqHE\n" +
                "WU7ybtgPr6KcwnbJw+SnjTX3WgsE0cW16TWFU7Vy9kcAYLSuZAXo2FZsbHz2i2klt4WY2b\n" +
                "QRt8I8/MfwIyRdkeBf4tMmWC9CwJuyVkf//n4SQAv6OL7mku6+a0zKivvyJke/dknueq9m\n" +
                "sDCoHr8EJZZoTcsLSXxE/miOR/t+Hp5rwzNT6gdd8N4uNlh2CA931+U/Ua7iO6USGo2rvf\n" +
                "emicfNfiYRo8ruxz3NZC0Kk6JN/ItelsRDokk8NgXftZ3T7irJ6U6+ztJoii844PfiAkPs\n" +
                "xcdRn8zOIksMGPr6ngw9dcdMZ/24pHjPciiM8oe/4j0M0wmdKZsTb0CATKGLcemkE84lFs\n" +
                "CJvyGNBaHP+nmPQACWRBDAOnm81qURZhZJdyCgkcAHd7zWm6AlOqONkjxrHppAnLAXS+JB\n" +
                "6lv+8zF2Wo8l7FeJts+kpLVsb7yy+fauZiKGgC1vQaoPcjOfR5gYnCUrdmzo0rlTvg+Jgn\n" +
                "gt7fQzs/DVSX8xvM4o30bNGINWVD9l7jTQk1Q55ZeB0ybvQ9MvrYXQafQtVNIKto4gNLhY\n" +
                "6cAYt0hhAosniZE6pH/b5mFBiFjS9fHRkPhE+soPY6x/faotmylh8pYiUTjHnoJrfMs7du\n" +
                "4aKrmGY6ytCi51YjUoNEA2J/QtQLmvTJf9UC2b8XStC8fSUzx2hHOmt5TEGa9F2LaTD2gS\n" +
                "/i5UGvwVEX9cN8dHyl83B7hKROYbV8bBBOMaS3edC9EDXwI+IYG6qiEj5NQGJcsLjOJOIe\n" +
                "5V8bwqwMZ5q0N8fi63Rzo2qYfQ+f21lBddqg1VlOTLPJ+5Mlf5sQbt+Eo31W/Y5foxoKa6\n" +
                "rA7RnmKtjs1CBk2DJHB6VEN1+SBRF/nGwsy484xntN98awHhZtOx8m8+9KB5EHNkyRwBQp\n" +
                "2D4s7/msqLft8oG4xnHCxfasTfPH5e5CHBjaMFuER+pGUsVQ+9ixt3q+75wa8dLzKZBWhP\n" +
                "Mvf+jlt8Uy4lC7stns5fOkfDmjENtLkPToNl6BQZzHYHF1M30RizrgiltXoKUTc1vFSRX7\n" +
                "6hcIZLi1iS2fD+wHe0RNxZV+19FF1IWDaxvyStBaAn1sq9LdwWHRF5kpjDi2r7eCYORBzf\n" +
                "u5b6zHPjLDVfC7Dl+7s/kqhiEcHs8EuIw7ET8C9jhpz9B4HQrWPictNvU+Izez9vyBiviL\n" +
                "+2cYSP7V4xwSI6obSl+MhnjAEjBYkwFK4Msu5LA9dDxxql5RKW89bJyWpvD4iri3SD2J45\n" +
                "xIWR/7r8an4Dpjg1F48doV20rW2FUawHvsH4s1/VqJkisSnpIQeu/R8sCdptEjIqKCObE7\n" +
                "2vVbzjfmCdq36MO4KZeVodLOIEW8i2/TbE107SPLJYGypX9miazQzWMRnBbncpykC0Opcq\n" +
                "SOIXyS7i0p56seABKulvc3BnSR4WnMzzJKTSfwaJCoJy0f7Bcv0kaIZLZUENpK2QwH+8I6\n" +
                "mVsiX7NtWWX4ALzMl9DiY7ni7oJm73p7L0HNsYpx6B6RG34Ua2wZevNVJe7cPGv1q0Jn8e\n" +
                "nI3pAuSB0vhFPyDPfHzgYsvx4fo5FcpurtdbuTG+QwCtrSbmSl8wwzHk8nRsFzkR1O2bCa\n" +
                "CxQyBM594SzM4/XjEw45gwoB6LLrHle58mp2j6eF94PexjqN7xdCjD1gdux5cvciIjALu4\n" +
                "wFtCBPvNM34z0nLP/1w1qOktQOls5/wqy7dTB5TDnmw158GLpnAJokpphsmkNj0WXSyjNE\n" +
                "Eg2OSQb49J8Rt8X2iL0Cx6yXprc=";
        privateKey = privateKey.replace("\n", "");
        String s = new String(Base64.getDecoder().decode(privateKey));
        System.out.println(s);
    }
}
