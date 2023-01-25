package com.tweet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweet.repositories.UserRepo;

@SpringBootTest
class TweetAppApplicationTests {



    @Autowired
    private UserRepo userRepo;
    
    
	@Test
	void contextLoads() {
	}
	
	@Test
	public void repotest()
	{
	    String className=this.userRepo.getClass().getName();
	    String packName=this.userRepo.getClass().getPackageName();
	    System.out.println(packName);
	    System.out.println(className);
	    
	}

}
