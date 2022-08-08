package com.demo.swaggerknife4jutils.utils.file.excel.utils;

import com.tingcheai.ctway.admin.client.NotPayOrderClient;
import com.tingcheai.ctway.admin.domain.pursue.NotPayOrderImport;
import com.tingcheai.ctway.bean.constant.dict.ImportTypeEnum;
import com.tingcheai.ctway.bean.pursue.order.NotPayOrdersData;
import com.tingcheai.ctway.bean.pursue.order.NotPayOrdersImport;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author yanxing
 * @date 2021/6/16 18:10
 */
@Slf4j
public class ExcelMultipartImport<T> {

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(0xFFFF);
    private ExecutorService exec = new ThreadPoolExecutor(2, 20,
            60, TimeUnit.SECONDS, queue,
            new DefaultThreadFactory("ExcelMultipartImport"));

    private NotPayOrderClient notPayOrderClient;

    /**
     * 总行数
     */
    private int maxRow;
    /**
     * 单条线程处理行数
     */
    private int pageRow = 200;

    /**
     * 导入的sheet
     */
    private List<T> sheet;
    /**
     * 任务号
     */
    private String traceId;

    public ExcelMultipartImport(List<T> sheet, String traceId, NotPayOrderClient notPayOrderClient) {
        this.maxRow = sheet.size();
        this.sheet = sheet;
        this.traceId = traceId;
        this.notPayOrderClient = notPayOrderClient;
    }

    public int start() {
        int totalPage = maxRow / pageRow;
        if (maxRow % pageRow > 0) {
            totalPage++;
        }

        for (int page = 0; page < totalPage; page++) {
            exec.execute(new DistributionWorker(page * pageRow, ((page + 1) * pageRow) - 1, traceId));
        }
        return 0;
    }


    class DistributionWorker implements Runnable {

        private int startRow;
        private int endRow;
        private String traceId;

        public DistributionWorker(int startRow, int endRow, String traceId) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.traceId = traceId;
            if (endRow > maxRow) {
                this.endRow = maxRow - 1;
            }
        }

        @Override
        public void run() {
            List<T> list = sheet.subList(startRow, endRow + 1);
            if (list != null && list.get(0) instanceof NotPayOrderImport) {
                NotPayOrdersImport data = new NotPayOrdersImport();
                data.setTraceId(traceId);
                data.setStartRow(startRow);
                data.setEndRow(endRow);
                data.setMaxRow(maxRow);
                data.setTypeOfData(ImportTypeEnum.IMPORT_ORDER.getCn());
                data.setNotPayOrdersDataList((List<NotPayOrdersData>) list);
                notPayOrderClient.importData(data, r -> {
                    if (null != r) {
                        if (r.getCode() == 200) {
                            log.info("任务[{}]A-->D,导入[{}-{}]行追缴订单操作成功:{}", traceId, startRow, endRow, r);
                        } else {
                            log.info("任务[{}]A-->D,导入[{}-{}]行追缴订单操作失败:{}", traceId, startRow, endRow, r);
                        }
                    } else {
                        log.warn("任务[{}]A-->D,导入[{}-{}]追缴订单,数据发送失败", traceId, startRow, endRow);
                    }
                });

            }
        }
    }
}
