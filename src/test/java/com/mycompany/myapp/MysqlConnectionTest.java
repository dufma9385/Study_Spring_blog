package com.mycompany.myapp;

import java.sql.Connection;

import javax.inject.*;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/dataSource-context.xml" })

public class MysqlConnectionTest {

	private static final Logger logger = LoggerFactory.getLogger(MysqlConnectionTest.class);

	@Inject
	private DataSource ds;
	
	@Test
	public void testConnection() {
		Connection con;
		try{
			con = ds.getConnection();
			logger.info("\n MySQL ¿¬°á : "+ con);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}