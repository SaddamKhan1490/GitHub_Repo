# WebSocket

WebSocket is a computer communications protocol, providing full-duplex communication channels over a single TCP connection. The WebSocket protocol enables interaction between a web client (such as a browser) and a web server with lower overheads, facilitating real-time data transfer from and to the server. 

WebSockets are bi directional communication protocol in Nature i.e. sender can send the data as well as receiver can also send the data(i.e. bi directional communication happens or takes place, Ex: ChatBot, Gamming, etc... )unlike HTTP were sender can only send the data whereas receiver will only send the response i.e. acknowledgement NO data, which results in one way communication (Ex: hitting a URL on browser). Therefore in order to acheive bi-directional communication web sockets dont close the connection once establish(i.e. connection is kept open untill any one of the two server voluntarily decide to close it or any one of the two server dies) unlike HTTP which closes connection everytime after receiving acknowledgement from server.

Whenever we hit a request URL everytime a new TCP connection will be opened between client and server, and for each request there will be response generated and send back to the client from server and once the response in received by client then this TCP connection gets closed i.e. per user request there exists one connection in HTTP. 

WebSockets are not prefered in cases where we are interested in getting back old data, this is where REST out performs WebSockets as we can retreive old data by hitting associated REST endpoint URL from browser. WebSockets are preferred in the cases where we are interested in the real time updates as WebSockets establish connection and keep that connection open(unitl server closes or gets killed) for any updates.

Extension for WebSockets is 'wss', whereas that for HTTP is 'http'.

WebSockets can be created using Java or JavaScript.

Purpose of this repository is to explore WebSockets with help of examples.
