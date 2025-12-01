package com.cs407.lab09

class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // Start in the middle of the field
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     *
     * Uses:
     *   v1 = v0 + 1/2 (a1 + a0) * Δt
     *   l  = v0 * Δt + 1/6 * Δt^2 * (3a0 + a1)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        val dt = dT

        // --- X direction ---
        val v0x = velocityX
        val a0x = accX
        val a1x = xAcc

        val v1x = v0x + 0.5f * (a1x + a0x) * dt
        val lx = v0x * dt + (1f / 6f) * dt * dt * (3f * a0x + a1x)

        velocityX = v1x
        posX += lx
        accX = a1x

        // --- Y direction ---
        val v0y = velocityY
        val a0y = accY
        val a1y = yAcc

        val v1y = v0y + 0.5f * (a1y + a0y) * dt
        val ly = v0y * dt + (1f / 6f) * dt * dt * (3f * a0y + a1y)

        velocityY = v1y
        posY += ly
        accY = a1y

        // Keep the ball on screen
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary are set to 0.
     */
    fun checkBoundaries() {
        val maxX = backgroundWidth - ballSize
        val maxY = backgroundHeight - ballSize

        // Left wall
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }
        // Right wall
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Top wall
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
        // Bottom wall
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f

        isFirstUpdate = true
    }
}
