package com.maccoy.rpc.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcProtocol {

    /**
     * 数据大小
     */
    private Integer len;

    /**
     * 数据内容
     */
    private byte[] data;

}
