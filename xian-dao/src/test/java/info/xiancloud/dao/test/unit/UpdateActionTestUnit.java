package info.xiancloud.dao.test.unit;

import info.xiancloud.core.Group;
import info.xiancloud.dao.group.unit.DaoUnit;
import info.xiancloud.dao.jdbc.sql.Action;
import info.xiancloud.dao.jdbc.sql.UpdateAction;
import info.xiancloud.dao.test.DaoTestGroup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author happyyangyuan
 */
public class UpdateActionTestUnit extends DaoUnit {
    @Override
    public Action[] getActions() {
        return new Action[]{
                new UpdateAction() {
                    @Override
                    public String table() {
                        return "ucs_privilege";
                    }

                    @Override
                    protected String sqlHeader(Map map, Connection connection) throws SQLException {
                        return "update ucs_privilege set privilege_name = {privilege_name}";
                    }

                    @Override
                    protected String[] where() {
                        return new String[]{"privilege_id={privilegeId}"};
                    }
                }
        };
    }

    @Override
    public String getName() {
        return "updateActionTest";
    }

    @Override
    public Group getGroup() {
        return DaoTestGroup.singleton;
    }

}
