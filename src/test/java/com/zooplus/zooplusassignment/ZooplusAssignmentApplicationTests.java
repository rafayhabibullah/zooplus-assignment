package com.zooplus.zooplusassignment;

import com.zooplus.zooplusassignment.model.ConversionResponse;
import com.zooplus.zooplusassignment.model.Currency;
import com.zooplus.zooplusassignment.model.Location;
import com.zooplus.zooplusassignment.service.CryptoService;
import com.zooplus.zooplusassignment.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
class ZooplusAssignmentApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LocationService locationService;

	@Autowired
	private CryptoService cryptoService;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
		assertNotNull(locationService);
	}

	@Test
	void shouldReturnCurrenciesList() throws Exception {
		this.mockMvc.perform(get("/currencies")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void shouldReturnConversionRate_For_Bitcoin() throws Exception {
		this.mockMvc.perform(get("/conversion?ipAddress=81.169.181.179&currency=BTC"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void shouldReturn_ListOfCryptoCurrencies() {
		List<Currency> response = cryptoService.listOfCryptoCurrencies();
		assertNotNull(response);
		assertFalse(response.isEmpty());
	}

	@Test
	void shouldReturnConversionRate_For_Ethereum() throws Exception {
		this.mockMvc.perform(get("/conversion?ipAddress=81.169.181.179&currency=ETH"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void shouldReturnDefaultLocation_EUR_GERMANY() {
		Location location = locationService.getCurrencyCodeByIP("0.0.0.0");

		assertNotNull(location);
		assertEquals("EUR", location.getCurrency());
		assertEquals("DE", location.getCountryCode());
	}

	@Test
	void shouldReturnCorrectLocation_GERMANY() {
		Location location = locationService.getCurrencyCodeByIP("81.169.181.179");

		assertNotNull(location);
		assertEquals("EUR", location.getCurrency());
		assertEquals("DE", location.getCountryCode());
	}

	@Test
	void shouldReturnCorrectLocation_USA() {
		Location location = locationService.getCurrencyCodeByIP("23.81.0.59");

		assertNotNull(location);
		assertEquals("USD", location.getCurrency());
		assertEquals("US", location.getCountryCode());
	}

	@Test
	void shouldReturnCorrectLocation_UAE() {
		Location location = locationService.getCurrencyCodeByIP("94.204.92.108");

		assertNotNull(location);
		assertEquals("AED", location.getCurrency());
		assertEquals("AE", location.getCountryCode());
	}

	@Test
	void shouldReturnValidCurrencyCode_EUR() {
		ConversionResponse response = cryptoService.convert("81.169.181.179", "BTC");

		assertNotNull(response);
		assertNotNull(response.getConversionRate());

		String[] conversionRate = response.getConversionRate().split(" ");
		assertEquals("EUR", conversionRate[0]);
	}

	@Test
	void shouldReturn_ZERO_ForInvalidCurrency() {
		ConversionResponse response = cryptoService.convert("81.169.181.179", "TFG");

		assertNotNull(response);
		assertNotNull(response.getConversionRate());

		String[] conversionRate = response.getConversionRate().split(" ");
		assertEquals("EUR", conversionRate[0]);
		assertEquals("0", conversionRate[1]);
	}

	@Test
	public void testLoadHomePage() throws Exception{
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

}
