package com.pokeya.yao.clientcache.query;

import com.pokeya.yao.clientcache.MatcherRegOption;
import com.pokeya.yao.constant.ResultCode;
import com.pokeya.yao.utils.ApiAssert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpRequest;

@Getter//只提供getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)//私有修饰，防止外部调用
public enum PathMatcherRegOptions implements MatcherRegOption {
    BLANK("_") {
        @Override
        public String find(HttpRequest request, String field) {
            return "_";
        }
    },
    QUERYSTRING("q") {
        @Override
        public String find(HttpRequest request, String field) {
            StringBuilder sb = new StringBuilder("&");
            String query = request.getURI().getQuery();
            ApiAssert.hasLength(query, ResultCode.COMMON_PARAMS_ERROR, "queryString is empty");
            int index = query.indexOf(sb.append(field).toString());
            if (index < 0) {
                sb = new StringBuilder("?");
                index = request.getURI().getQuery().indexOf(sb.append(field).toString());
            }
            int beginIndex = query.indexOf("=", index) + 1;
            int lastIndex = query.indexOf("&", beginIndex);
            return query.substring(beginIndex, lastIndex < 0 ? query.length() : lastIndex);
        }
    },
    TOKEN("t") {
        @Override
        public String find(HttpRequest request, String field) {
            String token = ApiAssert.isNotEmpty(request.getHeaders().get("token"), ResultCode.COMMON_CLIENT_CACHE_ERROR, "pathReg is t, but do not have token").get(0);
            //todo tokenUtil.get(field);
            return null;
        }
    };
    private final String reg;

    public abstract String find(HttpRequest request, String field);

    public static PathMatcherRegOptions getByReg(String reg) {
        for (PathMatcherRegOptions options : PathMatcherRegOptions.values()) {
            if (options.getReg().equals(reg)) {
                return options;
            }
        }
        return QUERYSTRING;
    }
}
