package com.example.Venus.dto.request;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author korashh waiba
 * @version v1.0
 * @since 12/22/2024
 **/
@Getter
@Setter
@AllArgsConstructor
public class RolesRequest {
//    private List<Long> selectedActions;
    private String name;
    private String description;
}
