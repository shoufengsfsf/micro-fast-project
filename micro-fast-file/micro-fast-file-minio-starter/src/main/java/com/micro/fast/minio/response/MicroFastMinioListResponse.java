package com.micro.fast.minio.response;

import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询响应结果
 *
 * @author shoufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MicroFastMinioListResponse {

    private List<BucketObjectListData> bucketObjectListDataList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BucketObjectListData {
        private Bucket bucket;
        private List<Item> objectList;
    }
}
