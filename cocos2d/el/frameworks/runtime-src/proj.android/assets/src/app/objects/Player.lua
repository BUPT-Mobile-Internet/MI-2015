local Player = class("Player", function ( )
	-- body
	return display.newSprite("#flying1.png")
end)

function Player:ctor(  )
	-- body
	local body = cc.PhysicsBody:createBox(self:getContentSize())

	body:setCategoryBitmask(0x0111)
	body:setContactTestBitmask(0x1111)
	body:setCollisionBitmask(0x1001)
	self:setTag(PLAYER_TAG)

	self:setPhysicsBody(body)
	self:addAnimationCache()
	self:getPhysicsBody():setGravityEnable(false)

end

function Player:addAnimationCache(  )
	-- body
	local animations = {"flying","drop","die"}
	local animationFrameNum = {4,2,4}

	for i = 1,#animations do
		--todo

		--[[创建一个包含animations[i]1.png到animations[i]animationFrameNum[i].png的图像帧对象的数组，如i ＝ 1，就是创建一个包含flying1.png到flying4.png的图像帧对象的数组。其中..是字符串连接操作符，它可以用来连接两个字符串。当其中一个为其它类型时，它会把该类型也转为字符串。
        以包含图像帧的数组创建一个动画 Animation 对象，参数 0.3 / 4 表示 0.3 秒播放完 4 桢。
        将2中创建好的 animation 对象以指定的名称（animations[i]）加入到动画缓存中，以便后续反复使用。也就是我们在 AnimationCache 中可以通过animations = {"flying", "drop", "die"}这三种动画的名称来查找制定的 animation 对象。
		--]]
		local frames = display.newFrames(animations[i].."%d.png",1,animationFrameNum[i])
		local animation = display.newAnimation(frames,0.3/4)
		display.setAnimationCache(animations[i],animation)

	end
end

function Player:flying( )
	-- body
	transition.stopTarget(self)
	transition.playAnimationForever(self, display.getAnimationCache("flying"))

end

function Player:drop( )
	-- body
	--停止所有的动作
	transition.stopTarget(self)
	transition.playAnimationForever(self, display.getAnimationCache("drop"))
	
end

function Player:die( )
	-- body
	transition.stopTarget(self)
	transition.playAnimationOnce(self, display.getAnimationCache("die"))
	
end

function Player:createProgress(  )
	self.blood = 100
	local progressbg = display.newSprite("image/progress1.png")
	self.fill = display.newProgressTimer("image/progress2.png",display.PROGRESS_TIMER_BAR)
	self.fill:setMidpoint(cc.p(0,0.5))
	self.fill:setBarChangeRate(cc.p(1.0,0))
	self.fill:setPosition(progressbg:getContentSize().width / 2, progressbg:getContentSize().height / 2)
	progressbg:addChild(self.fill)

	self.fill:setPercentage(self.blood)

	progressbg:align(display.TOP_LEFT, display.left,display.top )

	self:getParent():addChild(progressbg)

end

function Player:hit( )
	local hit = display.newSprite()
	hit:setPosition(self:getContentSize().width / 2, self:getContentSize().height / 2)
	self:addChild(hit)

	local frames = display.newFrames("attack%d.png",1,6)
	local animation = display.newAnimation(frames,0.3 / 6)
	local animate = cc.Animate:create(animation)

	local sequence = transition.sequence({
		animate,
		cc.CallFunc:create(function()
			hit:removeSelf()
		end)
		})
	hit:runAction(sequence)
	hit:setScale(0.6)


end


function Player:setProPercentage( percentage )
	self.fill:setPercentage(percentage)
end


return Player