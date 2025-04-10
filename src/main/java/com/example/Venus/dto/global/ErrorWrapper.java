package com.example.Venus.dto.global;

import lombok.*;
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
public class ErrorWrapper {
    private int errorCode;
    private String errorMessage;
}