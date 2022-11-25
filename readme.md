[![CodeFactor](https://www.codefactor.io/repository/github/akagiant/simplecoinflips/badge/dev)](https://www.codefactor.io/repository/github/akagiant/simplecoinflips/overview/dev)
## Permissions:
### Base: SimpleCoinFlips
|   Permission   |                                                                     Description                                                                      | Default | Children |
|:--------------:|------------------------------------------------------------------------------------------------------------------------------------------------------|:-------:|:--------:|
|    .create     |                                                         Allows the user to create CoinFlips                                                          |   NA    |    NA    | 
|   .create.#    |                                                   Allows the user to create X amount of CoinFlips                                                    |   NA    |    NA    |
|    .cancel     |                                               Allows the user to cancel one or more of their CoinFlips                                               |   NA    |    NA    |
|    .toggle     |                                    Allows the user to toggle if they are notified <br> by newly created CoinFlips                                    |   NA    |    NA    |
|     .stats     | Allows the user to view their on CoinFlip Stats <br>wins, amount won, amount lost, amount gambled<br> losses, times picked heads, timed picked tails |   NA    |    NA    |
|  .stats.other  |                                                Allows the user to view another players CoinFlip Stats                                                |   NA    |  .stats  |

### Admin Perms
|   Permission   |                                                                     Description                                                                      | Default | Children |
|:--------------:|------------------------------------------------------------------------------------------------------------------------------------------------------|:-------:|:--------:|
|       .*       |                                                  Gives the user access to all CoinFlip Permissions                                                   |   OP    |   All    |
| .cancel.force  |                                                  Allows the user to cancel another players CoinFlip                                                  |   OP    |    NA    |