package org.colorcoding.ibas.bobas.messages;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.colorcoding.ibas.bobas.MyConfiguration;

/**
 * 运行日志
 * 
 * @author Niuren.Zhu
 *
 */
public class RuntimeLog {

    public static final String MSG_PERMISSIONS_NOT_AUTHORIZED = "permissions: user [%s] call [%s - %s] is not authorized.";
    public static final String MSG_CONNECTION_USER_CONNECTED = "connection: user [%s] connected [%s|%s].";
    public static final String MSG_CONNECTION_USING_SERVER = "connection: using {%s}, user [%s] & server [%s] & db [%s].";
    public static final String MSG_SQL_SCRIPTS = "sql: %s";
    public static final String MSG_REPOSITORY_FETCH_AND_FILTERING = "repository: fetch [%s] [%s] times, result [%s] filtering [%s].";
    public static final String MSG_REPOSITORY_FETCHING_IN_CACHE = "repository: fetching [%s] in cache repository.";
    public static final String MSG_REPOSITORY_FETCHING_IN_DB = "repository: fetching [%s] in db repository.";
    public static final String MSG_REPOSITORY_FETCHING_IN_READONLY_REPOSITORY = "repository: fetching [%s] in readonly repository.";
    public static final String MSG_REPOSITORY_FETCHING_IN_MASTER_REPOSITORY = "repository: fetching [%s] in master repository.";
    public static final String MSG_REPOSITORY_DELETED_DATA_FILE = "repository: deleted data file [%s].";
    public static final String MSG_REPOSITORY_WRITED_DATA_FILE = "repository: writed data in file [%s].";
    public static final String MSG_REPOSITORY_CHANGED_USER = "repository: changed user [%s].";
    public static final String MSG_REPOSITORY_WRITE_FILE = "repository: writed file [%s].";
    public static final String MSG_PROPERTIES_GET_TYPE_PROPERTIES = "properties: get type [%s]'s properties.";
    public static final String MSG_PROPERTIES_REGISTER_PROPERTIES = "properties: register type [%s]'s property [%s].";
    public static final String MSG_PROPERTIES_NOT_FOUND_PROPERTIES = "properties: not found type [%s]'s property [%s].";
    public static final String MSG_DB_POOL_USING_CONNECTION = "db pool: using connection [%s].";
    public static final String MSG_DB_POOL_RECYCLED_CONNECTION = "db pool: recycled connection [%s].";
    public static final String MSG_JUDGMENT_EXPRESSION = "judgment: expression %s = [%s]";
    public static final String MSG_JUDGMENT_RELATION = "judgment: relation %s = [%s]";
    public static final String MSG_JUDGMENT_LINK_INFO = "judgment: judgment item count [%s].";
    public static final String MSG_DB_ADAPTER_CREATED = "db adapter: created db adapter [%s].";
    public static final String MSG_TRANSACTION_SP_VALUES = "transaction: sp [%s] [%s] [%s - %s]";
    public static final String MSG_USER_FIELDS_REGISTER = "user fields: register type [%s]'s user fields, count [%s].";
    public static final String MSG_USER_SET_FIELD_VALUE = "user fields: set field [%s]'s value [%s].";
    public static final String MSG_APPROVAL_PROCESS_MANAGER_CREATED = "ap manager: created approval process manager [%s].";
    public static final String MSG_APPROVAL_PROCESS_STARTED = "approval process: data [%s]'s approval process was started, name [%s].";
    public static final String MSG_APPROVAL_PROCESS_STATUS_CHANGED = "approval process: [%s]'s status change to [%s].";
    public static final String MSG_OWNERSHIP_JUDGER_CREATED = "judger: created ownership judger [%s].";
    public static final String MSG_BUSINESS_LOGICS_MANAGER_CREATED = "bl manager: created business logics manager [%s].";
    public static final String MSG_I18N_READ_FILE_DATA = "i18n: read file's data [%s].";
    public static final String MSG_I18N_RESOURCES_FOLDER = "i18n: use folder [%s].";
    public static final String MSG_CONFIG_READ_FILE_DATA = "config: read file's data [%s].";
    public static final String MSG_CONFIG_READ_FILE_DATA_FAILD = "config: read file's data [%s] faild.";
    public static final String MSG_LOGICS_RUNNING_LOGIC_FORWARD = "logics: forward logic [%s] by data [%s].";
    public static final String MSG_LOGICS_RUNNING_LOGIC_REVERSE = "logics: reverse logic [%s] by data [%s].";
    public static final String MSG_LOGICS_EXISTING_CONTRACT = "logics: class [%s] existing contract [%s].";
    public static final String MSG_LOGICS_CHAIN_REMOVED = "logics: chain [%s] was removed, because [%s].";
    public static final String MSG_LOGICS_CHAIN_CREATED = "logics: chain [%s] was created, by [%s].";
    public static final String MSG_BO_FACTORY_REGISTER_BO_CODE = "factory: register [%s] for [%s].";
    public static final String MSG_DAEMON_REGISTER_TASK = "daemon: register task id [%s], name [%s].";
    public static final String MSG_DAEMON_REMOVE_TASK = "daemon: remove task id [%s], name [%s].";
    public static final String MSG_DAEMON_TASK_START = "daemon: begin to run task [%s - %s], %sth running.";
    public static final String MSG_DAEMON_TASK_COMPLETED = "daemon: end task [%s - %s] %sth running and for [%s] milliseconds.";
    public static final String MSG_DAEMON_REGISTER_VOID_TASK = "daemon: register void task.";
    public static final String MSG_DAEMON_TASK_ALREADY_EXIST = "daemon: task [%s] already exist.";
    public static final String MSG_DAEMON_SINGLE_TASK_WORK_FOLDER_NOT_EXISTS = "daemon: single task work folder not exists.";
    public static final String MSG_BUSINESS_RULES_MANAGER_CREATED = "rules manager: created business rules manager [%s].";
    public static final String MSG_RULES_EXECUTING = "rules: bo %s executing rule [%s - %s].";
    public static final String MSG_RULES_EXECUTING_FAILD = "rules: field [%s] triggered rules fail to run, %s.";

    private static int messageLevel = -1;

    /**
     * 是否处于debug模式
     * 
     * @return
     */
    protected static int getMessageLevel() {
        // 访问频繁，提高下性能
        if (messageLevel == -1) {
            synchronized (RuntimeLog.class) {
                if (messageLevel == -1) {
                    if (MyConfiguration.isDebugMode()) {
                        messageLevel = MessageLevel.DEBUG.ordinal();
                    } else {
                        String value = MyConfiguration.getConfigValue(MyConfiguration.CONFIG_ITEM_LOG_MESSAGE_LEVEL);
                        if (value != null && !value.isEmpty()) {
                            MessageLevel level = MessageLevel.valueOf(value.toUpperCase());
                            messageLevel = level.ordinal();
                        } else {
                            messageLevel = MessageLevel.ERROR.ordinal();
                        }
                    }
                }
            }
        }
        return messageLevel;
    }

    private volatile static IMessageRecorder recorder;

    protected static IMessageRecorder getRecorder() {
        if (recorder == null) {
            synchronized (RuntimeLog.class) {
                if (recorder == null) {
                    recorder = RecorderFactory.createRecorder();
                }
            }
        }
        return recorder;
    }

    /**
     * 日记记录主要方法
     * 
     * @param message
     *            传递过来的消息
     */
    public static void log(IMessage message) {
        if (message == null) {
            return;
        }
        if (message.getLevel().ordinal() <= getMessageLevel()) {
            getRecorder().record(message);
        }
    }

    /**
     * 记录消息
     * 
     * @param message
     *            消息内容
     */
    public static void log(String message) {
        log(MessageLevel.INFO, message, "");
    }

    /**
     * 记录消息
     * 
     * @param level
     *            消息级别
     * @param message
     *            消息内容
     */
    public static void log(MessageLevel level, String message) {
        log(Message.create(level, message));
    }

    /**
     * 记录消息，带格式参数（message %s.）
     * 
     * @param message
     *            消息内容及格式
     * @param args
     *            格式中的参数
     */
    public static void log(String message, Object... args) {
        log(MessageLevel.INFO, message, args);
    }

    /**
     * 记录消息，带格式参数（message %s.）
     * 
     * @param level
     *            消息级别
     * @param message
     *            消息内容及格式
     * @param args
     *            格式中的参数
     */
    public static void log(MessageLevel level, String message, Object... args) {
        log(level, String.format(message, args));
    }

    /**
     * 记录消息
     * 
     * @param exception
     *            异常
     */
    public static void log(Exception e) {
        if (e == null) {
            return;
        }
        log(MessageLevel.ERROR, e);
    }

    /**
     * 记录消息
     * 
     * @param level
     *            消息级别
     * @param exception
     *            异常
     */
    public static void log(MessageLevel level, Exception e) {
        if (e == null) {
            return;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        log(level, stringWriter.toString());
    }

}
