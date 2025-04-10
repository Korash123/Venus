package com.example.Venus.dto.global;

import lombok.*;

import java.util.List;
/** Generic Request for all error
 * @author korash
 * @version v1.0
 * @since 2025-18-01
 **/
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Error {
    private List<ErrorWrapper> errorList;
}