package com.idt.aiowebflux.response;

import com.idt.aiowebflux.dto.AccountData;

public record DocAccountDataResponse(
        AccountData accountData,
        String auth

) {

}
