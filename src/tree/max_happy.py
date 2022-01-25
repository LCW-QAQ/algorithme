"""
用一个多叉树表示公司所有员工, 以及上下级关系, 假设不会出现跨部门的关系(任意节点a的子节点不会与a的兄弟节点有关系)
每个员工有一个happy值, 现在要邀请员工参加聚会, 不允许直接上下级的人都来参加聚会, 求最大happy值总和
"""
from dataclasses import dataclass
from typing import List


@dataclass
class Node:
    happy: int
    nodes: List["Node"]


@dataclass
class Info:
    # 当前节点受邀时最大收益
    yes: int
    # 当前节点不被邀请时最大收益
    no: int


def process(node):
    if not node:
        return Info(0, 0)
    # yes初始值为node.happy, 当前节点确定要来
    yes, no = node.happy, 0
    for item in node.nodes:
        # 获取员工受邀与没受邀的最大收益
        info = process(item)
        # 当前节点受邀, 直接子节点无法受邀, 加上子节点没来时的最大收益
        yes += info.no
        # 当前节点没受邀, 但是子节点是否收邀不一定, 加上子节点受邀或没受邀之中更大的那个收益
        no += max(info.yes, info.no)

    return Info(yes, no)


def max_happy(root: Node) -> int:
    info = process(root)
    return max(info.yes, info.no)
