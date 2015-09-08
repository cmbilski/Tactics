package com.tactics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class Util {

	public static void addInputProcessor(InputProcessor processor) {
		// Get the input multiplexer we are currently using
		InputMultiplexer input = (InputMultiplexer) Gdx.input.getInputProcessor();

		// We always want this to operate as a stack, so add it to the 0th index
		input.addProcessor(0, processor);
	}

	public static void removeInputProcessor(InputProcessor processor) {
		// Get the input multiplexer we are currently using
		InputMultiplexer input = (InputMultiplexer) Gdx.input.getInputProcessor();

		// Remove the desired processor
		input.removeProcessor(processor);
	}

	public static boolean inputContainsProcessor(InputProcessor processor) {
		// Get the input multiplexer we are currently using
		InputMultiplexer input = (InputMultiplexer) Gdx.input.getInputProcessor();

		// Return if the multiplexer contains the target processor by ==
		return input.getProcessors().contains(processor, true);
	}

}
