package com.pokeya.yao.clientcache.query;

import com.pokeya.yao.clientcache.ClientCacheConfigBean;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "clientcache")
public class ClientCacheQueryMatcher {
    /**
     * key sid cid stuId customize
     * key:资源key
     * sid/cid/stuId 使用_（无需），?(变量，根据pathReg规则获取)
     * customize 使用_（无需），或指定参数名称，如 uid、msgId等，默认使用'?'的处理方式，但是会取keyReg指定的参数
     * <p>
     * keyMatcher sid cid stuId customize
     * keyMatcher：匹配对应的key，如：通过url路径正则匹配到的都关联到这个keyConfig下
     * sid/cid/stuId/customize 使用_(无需)，t(从token里拿)，q（从queryString里拿）
     */
    Map<String, List<String>> keyConfigs;

    public String getKeyFromPath(String path) {
        return keyConfigs.entrySet().stream()
                .filter(keyConfig ->
                        keyConfig.getValue().stream().map(ClientCacheConfigBean::getSourceRule)
                                .anyMatch(path::matches))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public ClientCacheConfigBean getKeyConfigFromPath(String path) {

        for (Map.Entry<String, List<String>> entry : keyConfigs.entrySet()) {
            for (String matcher : entry.getValue()) {
                if (path.matches(ClientCacheConfigBean.getSourceRule(matcher))) {
                    return new ClientCacheConfigBean(entry.getKey(), matcher);
                }
            }
        }
        return null;
    }


}
