package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dto.StoreWorkerResDto;
import com.example.sidepot.work.dto.WorkRequestDto;
import com.example.sidepot.work.repository.query.WorkTimeDaoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "근무 조회 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class WorkQueryController {

    private final WorkTimeDaoRepository workTimeDaoRepository;

    @ApiOperation(value = "[매장] 오늘 근무 일정표", notes = "특정 날짜의 매장의 근무하는 직원들을 조회하는 API")
    //@PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/works/store/{storeId}/today")
    public ResponseEntity<ResponseDto> getStoreWorkToday(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                         @PathVariable Long storeId,
                                                         @RequestParam(value = "today")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today,
                                                         @ApiIgnore HttpServletRequest httpServletRequest) {
        List<StoreWorkerResDto> storeWorkToday = workTimeDaoRepository.getStoreWorkToday(storeId, today);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("오늘 근무 일정표")
                .data("")
                .build());
    }
}
