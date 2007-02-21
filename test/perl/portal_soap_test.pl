use Data::Dumper;
use SOAP::Lite;

my $endpoint = "http://localhost:8080/tunnel-web/secure/axis/Portal_UserService";
my $urn = "urn:http.service.portal.liferay.com";
my $userId = "liferay.com.1";
my $password = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";

my $client = SOAP::Lite->uri($urn)->proxy($endpoint);

my $user = $client->getUserByEmailAddress("liferay.com", "test\@liferay.com");

$user = $user->{'_content'}->[2]->[0]->[2]->[0]->[2]->[0]->[4];

#print Dumper($user);
print $user->{'greeting'};

sub SOAP::Transport::HTTP::Client::get_basic_credentials {
	return $userId => $password;
}