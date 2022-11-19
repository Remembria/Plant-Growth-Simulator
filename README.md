# Gardening Simulator
## Caring for virtual plants with virtual consequences

The goal of this application is twofold as follows:
- **To create** a gardening simulator for botany-enjoyers to garden without the stress of *real-world error*
- **To model** the growth of plants mathematically

Given these specifications, the target audience of this project will likely be: 
1. **Beginners** who are interested in getting into gardening
2. **Intermediate/Advanced** plant-enthusiasts who already take care of houseplants
3. **Game players** who enjoy idle genres that involve slow, relaxed gameplay

Personally, my own rationale for this project falls under all categories: I've always been interested in houseplants – 
but I'm a horrible plant-parent (*mine always die*), and so I usually stick to the tenacious but unimpressive cacti and 
succulents. Seeing as virtual plants can't really *die*, I saw the opportunity to practice my gardening skills in a Java 
environment as an enjoyable way to pass time and improve my mindfulness in regards to taking care of plants. What's 
more, I've already had an interest in **Lindenmayer systems**, which use formal grammar to create beautiful, 
fractal-esque drawings that mimic natural growth. By pursuing this project, I therefore hope to gain a better 
understanding of mathematical **L-systems** through their application within a programming environment. 

## Stories

- **As a user**, I want to be able to add plants to my garden
- **As a user**, I want to be able to remove plants from my garden
- **As a user**, I want to be able to view all plants within my garden
- **As a user**, I want my plants to grow over time when watered and cared for
- **As a user**, I want my plants to die if over-watered
- **As a user**, I want my plants to stop growing once they reach maturity
- **As a user**, I want to be able to save the state of my garden to a file
- **As a user**, I want to load a prior garden state from a file and continue playing
- **As a user**, I want plant growth to have an element of randomness

# Instructions for Grader

- **You can generate the first required event related to adding Xs to a Y by** selecting the *Add Plant* button.
    - You can give the plant any name, but the seed is written with letters F, +, - only.
    - Feel free to just put F for the seed (this is the simplest case)
    - To get the panel where all the (alive) plants are displayed, select the *View All Plants* button
- **You can generate the second required event related to adding Xs to a Y by** selecting the *Water Thirsty Plants*
button. This will give you a thirsty subset of the list of plants, and offers you the chance to water your most 
dehydrated plants (they can die from overwatering though!)
- **You can locate my visual component by** selecting the *View Plants* button
  and select the plant you've added. Depending on how quickly the plant grows (growthRate variable) you may see it
  grow in real time! Watch out though, plants die over time, so if a plant you've added is missing, this is the likely
  reason (dehydration).
- **You can save the state of my application by** selecting the *Save Garden* button
- **You can reload the state of my application by** electing the *Load Garden* button. Saved data will be overwritten.

### Citations:

Seralization JSON methods modelled upon repository https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git