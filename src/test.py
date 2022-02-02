import os

if __name__ == '__main__':
    for i in os.walk("."):
        print(i)
