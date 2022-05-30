package dynamic_processing

import (
	"testing"
)

func Test_RobotWalk(t *testing.T) {
	rw := RobotWalk{}
	res := rw.walkWays1(4, 2, 4, 4)
	t.Log(res)
}

func Test_RobotWalkDP(t *testing.T) {
	rw := RobotWalk{}
	res := rw.walkWaysDP(4, 2, 4, 4)
	t.Log(res)
}

func Test_Common(t *testing.T) {

}
