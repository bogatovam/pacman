export const port = {
  player: "http://localhost:9090",
  session: "http://localhost:8081",
};

export const routes = {
  newGame: port.player + "/queue/",
  wait: port.player + "/wait",
};
