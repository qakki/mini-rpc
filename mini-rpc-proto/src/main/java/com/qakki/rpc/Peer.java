package com.qakki.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络端点地址
 * @author qakki
 * @date 2021/1/16 4:32 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peer {

    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;

}
