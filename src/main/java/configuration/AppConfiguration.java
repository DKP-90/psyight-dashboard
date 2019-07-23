package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dashboard.response.campaigndetails.CampaignDetailProduct;
import com.dashboard.response.campaignlist.CampaignListList;
import com.dashboard.response.productdetails.Campaignlist;

@Configuration
public class AppConfiguration {

	@Bean
	public CampaignListList getCampaignListListBean() {
		return new CampaignListList();
	}
	
	@Bean
	public CampaignDetailProduct getCampaignDetailProductBean() {
		return new CampaignDetailProduct();
	}
	
	@Bean
	public Campaignlist getCampaignlistBean() {
		return new Campaignlist();
	}
}