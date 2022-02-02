import os

if __name__ == '__main__':
    all_exe = []
    for root, dirs, files in os.walk("."):
        for file in files:
            if file.endswith(".exe"):
                all_exe.append(os.path.join(root, file))
    print([x for x in all_exe])
    sure = input("您确定要删除以上exe文件吗 y/n ")
    if sure.lower() == "y":
        for item in all_exe:
            os.remove(item)
        print("删除成功")