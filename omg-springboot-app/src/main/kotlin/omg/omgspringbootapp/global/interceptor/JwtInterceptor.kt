package omg.omgspringbootapp.global.interceptor

import omg.omgspringbootapp.global.utils.jwt.JwtUtil
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtInterceptor(
    private val jwtUtil: JwtUtil
): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val bearerToken = request.getHeader("Access-Token") ?: throw IllegalArgumentException("필수 헤더값이 없습니다.")
        val accessToken = jwtUtil.resolveToken(bearerToken)
        val memberId = jwtUtil.getMemberIdFromToken(accessToken)

        // 추출한 멤버 ID를 요청 속성(attribute)에 저장하여 컨트롤러에서 사용할 수 있도록 합니다.
        request.setAttribute("memberId", memberId)

        return true
    }
}