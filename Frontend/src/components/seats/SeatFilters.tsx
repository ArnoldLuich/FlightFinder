import React, { useState, useEffect } from 'react';
import { Form } from 'react-bootstrap';
import { SeatFeature, SeatFiltersProps, featureEmojis } from '../../types/types';

export default function SeatFilters({
  numSeatsRequired,
  desiredFeatures,
  onNumSeatsChange,
  onFeaturesChange,
}: SeatFiltersProps) {
  const [inputValue, setInputValue] = useState(numSeatsRequired.toString());

  useEffect(() => {
    setInputValue(numSeatsRequired.toString());
  }, [numSeatsRequired]);

  // Handle user input, allowing only numeric values
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const val = e.target.value;
    if (val === '' || /^\d+$/.test(val)) setInputValue(val);
  };

  // Validate and apply number of seats on input
  const handleBlur = () => {
    const num = parseInt(inputValue, 10);
    if (!isNaN(num)) {
      onNumSeatsChange(num);
      setInputValue(num.toString());
    } else {
      onNumSeatsChange(0);
      setInputValue('');
    }
  };

  // Toggle seat features in the selected list
  const toggleFeature = (feature: SeatFeature) =>
    onFeaturesChange(
      desiredFeatures.includes(feature)
        ? desiredFeatures.filter(f => f !== feature)
        : [...desiredFeatures, feature]
    );

  return (
    <>
      {/* Input for number of required seats */}
      <Form.Group className="mb-3">
        <Form.Label>Number of seats needed:</Form.Label>
        <Form.Control
          type="text"
          value={inputValue}
          onChange={handleChange}
          onBlur={handleBlur}
        />
      </Form.Group>

      {/* Checkbox selection for desired seat features */}
      <Form.Group className="mb-3">
        <Form.Label>Desired features:</Form.Label>
        <div>
          {Object.entries(featureEmojis).map(([feature, emoji]) => (
            <Form.Check
              key={feature}
              type="checkbox"
              inline
              label={<>{emoji} {feature.replace('_', ' ')}</>}
              checked={desiredFeatures.includes(feature as SeatFeature)}
              onChange={() => toggleFeature(feature as SeatFeature)}
            />
          ))}
        </div>
      </Form.Group>
    </>
  );
}