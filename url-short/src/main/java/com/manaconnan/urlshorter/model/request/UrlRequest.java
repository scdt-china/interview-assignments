package com.manaconnan.urlshorter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    @NonNull
    private String url;

}
