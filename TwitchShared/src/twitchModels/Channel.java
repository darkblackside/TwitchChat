package twitchModels;

import java.util.Date;

public class Channel
{
	private Boolean mature;
	private String status;
	private String broadcaster_language;
	private String display_name;
	private String game;
	private String language;
	private String name;
	private Date created_at;
	private Date updated_at;
	private Integer delay;
	private String logo;
	private String banner;
	private String video_banner;
	private String background;
	private String profile_banner;
	private String profile_banner_background_color;
	private Boolean partner;
	private String url;
	private Integer views;
	private Integer followers;
	
	public Boolean isMature() {
		return mature;
	}
	public void setMature(Boolean mature) {
		this.mature = mature;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBroadcasterLanguage() {
		return broadcaster_language;
	}
	public void setBroadcasterLanguage(String broadcaster_language) {
		this.broadcaster_language = broadcaster_language;
	}
	public String getDisplayName() {
		return display_name;
	}
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdatedAt() {
		return updated_at;
	}
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer i)
	{
			this.delay = i;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getVideoBanner() {
		return video_banner;
	}
	public void setVideoBanner(String video_banner) {
		this.video_banner = video_banner;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getProfileBanner() {
		return profile_banner;
	}
	public void setProfileBanner(String profile_banner) {
		this.profile_banner = profile_banner;
	}
	public String getProfileBannerBackgroundColor() {
		return profile_banner_background_color;
	}
	public void setProfileBannerBackgroundColor(String profile_banner_background_color) {
		this.profile_banner_background_color = profile_banner_background_color;
	}
	public Boolean isPartner() {
		return partner;
	}
	public void setPartner(Boolean partner) {
		this.partner = partner;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public Integer getFollowers() {
		return followers;
	}
	public void setFollowers(Integer followers) {
		this.followers = followers;
	}
}
