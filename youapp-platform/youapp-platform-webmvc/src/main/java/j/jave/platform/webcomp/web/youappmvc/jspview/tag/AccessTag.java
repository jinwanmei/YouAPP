/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.jspview.tag;


/**
 * @author J
 */
@Deprecated
public class AccessTag {
//
//implements Tag{
//
//	 private PageContext pageContext;
//	 
//	 private Tag parent;
//	 
//	 private String resource;
//	
//	 private AuthenticationAccessService loginAccessService=
//			 JServiceHubDelegate.get().getService(this,AuthenticationAccessService.class);
//	 
//	public String getResource() {
//		return resource;
//	}
//
//	public void setResource(String resource) {
//		this.resource = resource;
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
//	 */
//	@Override
//	public int doEndTag() throws JspException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
//	 */
//	@Override
//	public int doStartTag() throws JspException {
//
//		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
//		//String userName=pageContext.get
//		ServletHttpContext httpContext=YouAppMvcUtils.getHttpContext(request);
//		boolean authorized=loginAccessService.authorizeOnUserId(resource, httpContext.getUser().getUserId());
//		if(!authorized){
//			return SKIP_BODY;
//		}
//		return EVAL_BODY_INCLUDE;
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#getParent()
//	 */
//	@Override
//	public Tag getParent() {
//		return this.parent;
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#release()
//	 */
//	@Override
//	public void release() {
//		// TODO Auto-generated method stub
//
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#setPageContext(javax.servlet.jsp.PageContext)
//	 */
//	@Override
//	public void setPageContext(PageContext arg0) {
//		this.pageContext=arg0;
//	}
//
//	/* (non-Javadoc)
//	 * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
//	 */
//	@Override
//	public void setParent(Tag arg0) {
//		this.parent=arg0;
//	}

}
