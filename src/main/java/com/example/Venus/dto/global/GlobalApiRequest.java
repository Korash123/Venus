package com.example.Venus.dto.global;/*
    @created 01/28/2025 9:30 PM
    @project iam
    @author korash waiba
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/** Generic Request for all the requests to the system with data, searching, sorting and pagination criteria
 * @author korash
 * @version v1.0
 * @since 2025-18-01
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GlobalApiRequest<T> {

    @JsonProperty("data")
    @Valid
    private T data;

    @JsonProperty("dataList")
    @Valid
    private List<T> dataList;

    @JsonProperty("pageIndex")
    private Integer pageIndex;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("searchCriteria")
    private Map<String, String> searchCriteria;

    @JsonProperty("filterCriteria")
    private Map<String, Object> filterCriteria;

    @JsonProperty("sortCriteria")
    private Map<String, String> sortCriteria;

    @JsonProperty("sortOrder")
    private String sortOrder;

}