def xml = '''
<hosts>
  <host name='MyHost'>
    <service name='MyMicroService'/>
    <service name='MyNanoService'/>
  </host>
</hosts>
'''
def hosts = new XmlParser().parseText(xml)
assert hosts.host.service[0].@name=='MyMicroService'
