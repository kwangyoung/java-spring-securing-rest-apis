package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final ResolutionRepository resolutions;
	private final UserRepository users;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository users) {
		this.resolutions = resolutions;
		this.users = users;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

		User user = new User( "user", "{bcrypt}$2a$10$KlHkRDPf0btP.5yvMVlS4evFmUxKPlFRm0FpI.clk/XuHDM28Xnuy" );
		user.setFullName( "User Userson" );
		user.grantAuthority( "resolution:read" );
		user.grantAuthority( "user:read" );
		user.grantAuthority( "resolution:write" );
		this.users.save( user );

		User hasread = new User();
		hasread.setUsername( "hasread" );
		hasread.setFullName( "Has Read" );
		hasread.setPassword( "{bcrypt}$2a$10$KlHkRDPf0btP.5yvMVlS4evFmUxKPlFRm0FpI.clk/XuHDM28Xnuy" );
		hasread.grantAuthority( "resolution:read" );
		hasread.grantAuthority( "user:read" );
		this.users.save( hasread );

		User haswrite = new User();
		haswrite.setUsername( "haswrite" );
		haswrite.setFullName( "Has Write" );
		haswrite.addFriend( hasread );
		haswrite.setSubscription( "premium" );
		haswrite.setPassword( "{bcrypt}$2a$10$KlHkRDPf0btP.5yvMVlS4evFmUxKPlFRm0FpI.clk/XuHDM28Xnuy" );
		haswrite.grantAuthority( "resolution:write" );
		haswrite.grantAuthority( "user:read" );
		this.users.save( haswrite );

		User admin = new User( "admin", "{bcrypt}$2a$10$KlHkRDPf0btP.5yvMVlS4evFmUxKPlFRm0FpI.clk/XuHDM28Xnuy" );
		admin.setFullName( "Admin Adminson" );
		admin.grantAuthority( "ROLE_ADMIN" );
		admin.grantAuthority( "user:read" );
		this.users.save( admin );
	}
}
