package kr.or.ddit.controller.noticeboard.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.test.DDITMemberVO;

@Controller
@RequestMapping("/notice")
public class NoticeProfileController {
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/profile.do", method = RequestMethod.GET)
	public String noticeProfile(
			HttpServletRequest req, RedirectAttributes ra, Model model
			) {
		String goPage = "";
		HttpSession session = req.getSession();
		DDITMemberVO sessionMember = (DDITMemberVO) session.getAttribute("SessionInfo");
		
		if (sessionMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용가능합니다!"); // 일회성 데이터 전달함. 새로고침해도 안뜸
			return "redirect:/notice/login.do";
		}
		
		DDITMemberVO member = noticeService.selectMember(sessionMember);
		
		if (member != null) {
			model.addAttribute("member", member);
			goPage = "notice/profile";
		}else {
			ra.addFlashAttribute("message", "로그인 후 이용가능합니다!");
			goPage = "redirect:/notice/login.do";
		}
		return goPage;
		
	}
	
	@RequestMapping(value = "/profileUpdate.do", method = RequestMethod.POST)
	public String noticeProfileUpdate(
			HttpServletRequest req, RedirectAttributes ra, Model model, DDITMemberVO memberVO
			) {
		String goPage = "";
		ServiceResult result = noticeService.profileUpdate(req, memberVO);
		
		if (result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "회원정보 수정이 완료되었습니다!");
			goPage = "redirect:/notice/profile.do";
		}else {
			model.addAttribute("member", memberVO);
			goPage = "notice/profile";
		}
		return goPage;
	}
}
