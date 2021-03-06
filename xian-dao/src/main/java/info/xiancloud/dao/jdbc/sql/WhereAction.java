package info.xiancloud.dao.jdbc.sql;

import info.xiancloud.core.util.StringUtil;
import info.xiancloud.dao.group.unit.DaoUnit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * 本类单一职责：拼装where条件
 *
 * @author happyyangyuan
 */
public abstract class WhereAction extends AbstractAction {

    protected abstract String[] where();

    protected abstract String sqlHeader(Map map, Connection connection) throws SQLException;

    protected String sqlPattern(Map map, Connection connection) throws SQLException {
        return sqlHeader(map, connection).concat(buildWhere(map)).concat(" ").concat(sqlTail(daoUnit, map, connection));
    }

    /**
     * @return 它应当返回sql语句的整个尾部
     */
    protected String sqlTail(DaoUnit daoUnit, Map map, Connection connection) {
        return "";
    }

    String buildWhere(Map map) {
        String where = " where ";
        for (String whereFragment : where()) {
            if (!ignoreWhereFragment(whereFragment, map)) {
                where = where.concat(" ").concat(adjustWhereFragment(where, whereFragment));
            }
        }
        if (where.endsWith(" where ")) {
            where = where.substring(0, where.length() - " where ".length());
        }
        return where;
    }

    private String adjustWhereFragment(String former, String fragment) {
        former = former.trim().toLowerCase();
        String lower = fragment.trim().toLowerCase();
        if (!former.endsWith(" and") && !former.endsWith(" or") && !former.endsWith("where")) {
            fragment = lower.startsWith("and ") || lower.startsWith("or ") ? fragment : " and ".concat(fragment);
        }
        PatternUtil.adjustLikeClause(fragment, map);
        return fragment;
    }


    final boolean ignoreWhereFragment(String whereFragment, Map map) {
        for (Object value : PatternUtil.getValues(whereFragment, map)) {
            if (value == null) {
                return true;
            }
            // 属性值是一个集合，那么如果集合size为0，那么直接忽略此查询条件
            if (value instanceof Collection && ((Collection) value).isEmpty()) {
                return true;
            }
            if (StringUtil.isEmpty(value)) {
                // 这里认为空串“”也是空，因此使用StringUtil.isEmpty()做判断
                return true;
            }
        }
        return false;
    }
}
