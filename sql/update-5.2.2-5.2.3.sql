update Group_ set name = classPK where classPK > 0 and name = '';

update Region set regionCode = 'AB' where countryId = 1 and name = 'Alberta';