const protocolPrefix = {
  http: "http://",
  ws: "ws://",
};

export const port = {
  player: "localhost:9090",
  session: "localhost:8081",
};

export const routes = {
  newGame: protocolPrefix.http + port.player + "/queue/",
  wait: protocolPrefix.ws + port.player + "/wait?userId=",
  getAllSession: protocolPrefix.http + port.session + "/session",
  connectToSession: protocolPrefix.http + port.session + "/session/",
  checkSession: protocolPrefix.ws + port.session + "/change?sessionId=",
};
