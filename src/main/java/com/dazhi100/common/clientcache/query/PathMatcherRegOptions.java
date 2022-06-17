package com.dazhi100.common.clientcache.query;

import com.dazhi100.common.clientcache.MatcherRegOption;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.utils.ApiAssert;
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
            int lastIndex = query.indexOf("&", index);
            return query.substring(index, lastIndex < 0 ? query.length() : lastIndex);
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
