package com.example.shiro.cache;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author csh9016
 * @date 2019/12/20
 */
public class CacheJdbcRealm extends JdbcRealm {

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return "web:author:" + principals.getPrimaryPrincipal();
    }
}
