#!/usr/bin/env python3

import sys
import time
import random
import socket
from optparse import OptionParser


MAX_MOVE = 81
EMPTY = 2
ILLEGAL_MOVE = 0
WIN = 2
LOSS = 3
DRAW = 4
TRIPLE = 5
TIMEOUT = 6
FULL_BOARD = 7


def agent_parse_args():
    usage = "usage: %prog [options]"
    parser = OptionParser(usage=usage)
    parser.remove_option("-h")
    parser.add_option(
        "-p", dest="port", help="choose a port to connect", metavar="port", type="int")
    parser.add_option("-h", dest="host", help="specific the host",
                      metavar="host", type="string")
    options, args = parser.parse_args()
    return options, args


def get_cause(str_cause):
    cause = TRIPLE
    if str_cause == "triple).":
        cause = TRIPLE
    elif str_cause == "timeout).":
        cause = TIMEOUT
    elif str_cause == "illegal_move).":
        cause = ILLEGAL_MOVE
    elif str_cause == "full_board).":
        cause = FULL_BOARD
    return cause


class Board:
    """class represents a board"""

    def __init__(self):
        self.board = [[EMPTY for _ in range(10)] for _ in range(10)]
        self.sb = "XO.xo"

    def reset(self):
        for i in range(len(self.board)):
            for j in range(len(self.board[0])):
                self.board[i][j] = EMPTY

    def get_value(self, i, j):
        return self.board[i][j]

    def set_value(self, i, j, value):
        self.board[i][j] = value

    def _print_row(self, a, b, c, i, j, k):
        print("%s %s %s |" % (
            self.sb[self.board[a][i]], self.sb[self.board[a][j]], self.sb[self.board[a][k]]), end="")
        print("%s %s %s |" % (
            self.sb[self.board[b][i]], self.sb[self.board[b][j]], self.sb[self.board[b][k]]), end="")
        print("%s %s %s" % (
            self.sb[self.board[c][i]], self.sb[self.board[c][j]], self.sb[self.board[c][k]]))

    def print_board(self, board_num, prev_move):
        # upper case
        self.board[board_num][prev_move] += 3
        self._print_row(1, 2, 3, 1, 2, 3)
        self._print_row(1, 2, 3, 4, 5, 6)
        self._print_row(1, 2, 3, 7, 8, 9)
        print("------+-------+------")
        self._print_row(4, 5, 6, 1, 2, 3)
        self._print_row(4, 5, 6, 4, 5, 6)
        self._print_row(4, 5, 6, 7, 8, 9)
        print("------+-------+------")
        self._print_row(7, 8, 9, 1, 2, 3)
        self._print_row(7, 8, 9, 4, 5, 6)
        self._print_row(7, 8, 9, 7, 8, 9)
        print("\n")
        # lower case
        self.board[board_num][prev_move] -= 3


class Agent:
    """class represents an agent"""

    def __init__(self):
        random.seed(time.time())
        self.board = Board()
        self.move = [0 for _ in range(MAX_MOVE + 1)]
        self.player = 0
        self.m = 0

    def start(self, this_player):
        self.board.reset()
        self.m = 0
        self.move[self.m] = 0
        self.player = this_player
        self.other_player = None
        if self.player = "X":
            self.other_player = "O"
        else:
            self.other_player = "X"

    def _get_input(self, prev_move):
        while True:
            try:
                this_move = int(input("your move: ").strip())
                if this_move <= 0 or this_move > 9:
                    continue
                if self.board.get_value(prev_move, this_move) == EMPTY:
                    break
            except:
                pass
        return this_move

    def choose_move(board_num):
        children = get_children_at_board(board_num)
        for child in children:
            minimax_func(child, True)
    
    def minimax_func(location, is_player_x):
        children = get_children_at_board(node)
        if children is None:
            return 0
        if three_in_a_row(location[0]):
            return 10
        if is_player_x:
            self.board[location[0]][location[1]] = self.player
        else:
            self.board[location[0]location[1]] = self.other_player

        def three_in_a_row(board):
            res = False
            board = self.board[location[0]]
            if board[0] == board[1] == board[2]:
                return True
            if board[3] == board[4] == board[5]:
                return True
            if board[6] == board[7] == board[8]:
                return True
            if board[0] == board[3] == board[6]:
                return True
            if board[1] == board[4] == board[7]:
                return True
            if board[2] == board[5] == board[8]:
    

                
    def get_children_at_node(board_num):
        board = self.board[board_num]
        ret = []
        has_children = False
        for i in range(9):
                if board[i] == EMPTY:
                    ret.append(board_num, i)
                    has_children = True
        if has_children is False:
            return None
        return ret
    
    def second_move(self, board_num, prev_move):
        self.move[0], self.move[1] = board_num, prev_move
        self.board.set_value(board_num, prev_move, 1 - self.player)
        self.m = 2
        self.board.print_board(board_num, prev_move)
        this_move = self.choose_move(board_num)
        self.move[self.m] = this_move
        self.board.set_value(prev_move, this_move, self.player)
        self.board.print_board(board_num, prev_move)
        return this_move

    def third_move(self, board_num, first_move, prev_move):
        self.move[0], self.move[1], self.move[2] = board_num, first_move, prev_move
        self.board.set_value(board_num, first_move, self.player)
        self.board.set_value(first_move, prev_move, 1 - self.player)
        self.m = 3
        self.board.print_board(first_move, prev_move)
        this_move = self._get_input(prev_move)
        self.move[self.m] = this_move
        self.board.set_value(self.move[self.m-1], this_move, self.player)
        self.board.print_board(first_move, prev_move)
        return this_move

    def next_move(self, prev_move):
        self.m += 1
        self.move[self.m] = prev_move
        self.board.set_value(
            self.move[self.m-1], self.move[self.m], 1 - self.player)
        self.m += 1
        self.board.print_board(self.move[self.m-1], self.move[self.m])
        this_move = self._get_input(prev_move)
        self.move[self.m] = this_move
        self.board.set_value(self.move[self.m-1], this_move, self.player)
        self.board.print_board(self.move[self.m-1], self.move[self.m])
        return this_move

    def last_move(self, prev_move):
        self.m += 1
        self.move[self.m] = prev_move
        self.board.set_value(
            self.move[self.m-1], self.move[self.m], 1 - self.player)
        self.board.print_board(self.move[self.m-1], self.move[self.m])

    def gameover(self, result, cause):
        D = {
            WIN: self.win,
            LOSS: self.loss,
            DRAW: self.draw,
        }
        Dict = {
            0: "ILLEGAL_MOVE",
            2: "WIN",
            3: "LOSS",
            4: "draw",
            5: "triple",
            6: "timeout",
            7: "full_board",
        }
        D[result](Dict[cause])

    def cleanup(self):
        pass

    def win(self, cause):
        print(f"you win! ({cause})")

    def loss(self, cause):
        print(f"you loss! ({cause})")

    def draw(self, cause):
        print(f"draw! ({cause})")

    def end(self, cause):
        print(f"end! ({cause})")


class Client:
    """class represents a client"""

    def __init__(self, port, host, agent):
        self.conn = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.conn.connect((host, port))
        self.agent = agent

    def second_move(self, board_num, prev_move):
        this_move = self.agent.second_move(board_num, prev_move)
        self.send(this_move)

    def third_move(self, board_num, first_move, prev_move):
        this_move = self.agent.third_move(board_num, first_move, prev_move)
        self.send(this_move)

    def next_move(self, prev_move):
        this_move = self.agent.next_move(prev_move)
        self.send(this_move)

    def cleanup(self):
        self.agent.cleanup()
        self.conn.close()
        sys.exit(0)

    def recv(self):
        data = []
        while True:
            c = self.conn.recv(1).decode()
            if c == '\n' or len(data) > 20 or c == '':
                break
            data.append(c)
        return "".join(data)

    def send(self, move):
        move = (str(move) + "\n").encode("utf8")
        self.conn.send(move)


def main():
    options, args = agent_parse_args()
    port, host = options.port if options.port else 31415, options.host if options.host else "localhost"

    # init here, init a agent and a client
    agent = Agent()
    client = Client(port, host, agent)

    # game loop
    while True:
        data = client.recv()
        if (data == "init."):
            pass
        elif "start" in data:
            player = 0 if data[6:-2] == 'x' else 1
            agent.start(player)
        elif "second_move" in data:
            board_num, prev_move = [int(i) for i in data[12:-2].split(",")]
            client.second_move(board_num, prev_move)
        elif "third_move" in data:
            board_num, first_move, prev_move = [
                int(i) for i in data[11:-2].split(",")]
            client.third_move(board_num, first_move, prev_move)
        elif "next_move" in data:
            prev_move = int(data[10:-2])
            client.next_move(prev_move)
        elif "last_move" in data:
            prev_move = int(data[10:-2])
            agent.last_move(prev_move)
        elif "win(" < data < "win)":
            result = WIN
            cause = get_cause(data[4:])
            agent.gameover(result, cause)
        elif "loss(" < data < "loss)":
            result = LOSS
            cause = get_cause(data[5:])
            agent.gameover(result, cause)
        elif "draw(" < data < "draw)":
            result = DRAW
            cause = get_cause(data[5:])
            agent.gameover(result, cause)
        elif data == "end.":
            client.cleanup()
            break

    return 0


if __name__ == "__main__":
    main()
