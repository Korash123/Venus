package com.example.Venus.dto.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
/** Generic Request for all the response
 * @author korash
 * @version v1.0
 * @since 2025-18-01
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalApiResponse<T> {
    private Error error;
    private String responseCode;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private T data;
    private List<T> datalist;
    private Integer totalRecords;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPages;
}