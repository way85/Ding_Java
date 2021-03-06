package cn.jpush.alertme.factory.plugins.zuimeia;

import cn.jpush.alertme.factory.common.BaseResource;
import cn.jpush.alertme.factory.common.QuartzHelper;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

/**
 * 最美应用 Resource
 * Created by ZeFanXie on 14-12-23.
 */
@Path("/zuimeia")
public class ZuiMeiAResource extends BaseResource {
    private static final Logger Log = LoggerFactory.getLogger(ZuiMeiAResource.class);
    public static final String Tag = "ZuiMeiA";
    // 每半小时执行一次
    private static final String cron = "0 */30 * * * ?";

    @Override
    protected boolean onServiceCreate(HttpServletResponse resp, String sid, String apiSecret, String title, Map<String, String> sData, List<CData> cData) {
        // 添加单例定时任务
        try {
            QuartzHelper.addSingleJob(Tag, cron, SpiderJob.class);
        } catch (SchedulerException e) {
            Log.error(Tag + " Quartz Init Fail:" + e.getMessage());
        }

        Log.debug(Tag + " Timer Running...");
        return super.onServiceCreate(resp, sid, apiSecret, title, sData, cData);
    }

    @Override
    protected Logger getLog() {
        return Log;
    }

    @Override
    protected String getTag() {
        return Tag;
    }
}
